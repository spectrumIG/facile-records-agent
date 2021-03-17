package it.facile.records.agent.recorddetail

import android.content.Intent
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.format.Formatter
import android.view.View
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import it.facile.records.agent.BaseFragment
import it.facile.records.agent.R
import it.facile.records.agent.databinding.RecordDetailFragmentBinding
import timber.log.Timber

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBindings = RecordDetailFragmentBinding.bind(view)

        val listAdapter = FileForRecordListAdapter()

        listOfFile = binding.fileListRecycler
        listOfFile.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }

        titleText.text = args.recordName

        detailViewModel.fetchRecordDetail(args.recordId)


        val addCallback = requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(R.id.beersListFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, addCallback)
        addFileButton.setOnClickListener {
            createIntent()
        }
        detailViewModel.fileForRecord.observe(viewLifecycleOwner){ listOfFile ->
            listAdapter.setData(listOfFile)
        }

//        detailViewModel.showError.observe(viewLifecycleOwner) { show ->
//            if(show) {
////              AlertDialog.Builder(requireContext())
////                  .setMessage(getString(R.string.alert_error_message))
////                  .setTitle(getString(R.string.error_alert_title))
////                  .setPositiveButton(android.R.string.ok) { dialog, _ ->
////                      dialog.dismiss()
////                      findNavController().navigate(R.id.beersListFragment)
////                  }
////                  .create().show()
//            }
//        }

    }

    private fun createIntent() {
        startActivityForResult(
            Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
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

            Timber.d(cursor.getString(nameIndex))
            Timber.d(Formatter.formatShortFileSize(requireContext(), cursor.getLong(sizeIndex)))

            detailViewModel.attachFileToRecord(
                recordId = args.recordId,
                filename = cursor.getString(nameIndex),
                filesize = cursor.getLong(sizeIndex))

        }
    }
}