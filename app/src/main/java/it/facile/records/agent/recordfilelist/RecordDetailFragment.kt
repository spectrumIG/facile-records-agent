package it.facile.records.agent.recordfilelist

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import it.facile.records.agent.BaseFragment
import it.facile.records.agent.R
import it.facile.records.agent.databinding.RecordDetailFragmentBinding

const val REQUEST_CODE = 10001

@AndroidEntryPoint
class RecordDetailFragment : BaseFragment(R.layout.record_detail_fragment) {
    private val args: RecordDetailFragmentArgs by navArgs()

    private val detailViewModel: RecordDetailViewModel by activityViewModels()

    private var fragmentBindings: RecordDetailFragmentBinding? = null
    private val binding get() = fragmentBindings!!

    private lateinit var listOfFile: RecyclerView

    private val addFileButton: FloatingActionButton
        get() {
            return binding.addFileButton
        }
    private val titleText: TextView
        get() {
            return binding.recordDetName
        }

    private val pullToRefresh: SwipeRefreshLayout
        get() {
            return binding.listMainRefreshContainer
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBindings = RecordDetailFragmentBinding.bind(view)

        val listAdapter = FileForRecordListAdapter { recordName: String ->
            createDeleteFileAlertDialog(recordName)
        }

        listOfFile = binding.fileListRecycler
        listOfFile.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }

        pullToRefresh.isRefreshing = true
        pullToRefresh.setOnRefreshListener { pullToRefresh.isRefreshing = false }  //I use the pull to refresh in this case just for UI
                                                                                   // notification not for actually reloading anything
        titleText.text = args.recordName

        detailViewModel.fetchRecordDetail(args.recordId)

        addFileButton.setOnClickListener {
            createIntent()
        }

        detailViewModel.fileForRecord.observe(viewLifecycleOwner) { listOfFile ->
            listAdapter.setData(listOfFile)
            pullToRefresh.isRefreshing = false
        }

        manageBackNavigation()
    }

    private fun createDeleteFileAlertDialog(recordName: String) {
        AlertDialog.Builder(requireContext()).setMessage(getString(R.string.dialog_text_label)).setTitle(getString(R.string.dialog_title_label))
            .setPositiveButton(android.R.string.ok) { _: DialogInterface, _: Int ->

                detailViewModel.deleteAttachedFileById(recordName, args.recordId)

            }.setNeutralButton(android.R.string.cancel) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }.create().show()
    }

    private fun manageBackNavigation() {
        val addCallback = requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(R.id.beersListFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, addCallback)
    }

    private fun createIntent() {
        startActivityForResult(
            Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*" //Since I didn't get if there's some file type limitations I assumed every kind of file has to be considered valid
                flags = flags or Intent.FLAG_GRANT_READ_URI_PERMISSION

            },
            REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        createFileInfoForPersistence(data)
    }

    private fun createFileInfoForPersistence(data: Intent?) {
        data?.data?.let { uri ->
            requireContext().contentResolver.query(uri, null, null, null, null)
        }?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            cursor.moveToFirst()
            detailViewModel.attachFileToRecord(
                recordId = args.recordId,
                filename = cursor.getString(nameIndex),
                filesize = cursor.getLong(sizeIndex))

        }
    }
}