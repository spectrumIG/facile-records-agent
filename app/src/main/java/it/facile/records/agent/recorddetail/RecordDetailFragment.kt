package it.facile.records.agent.recorddetail

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import it.facile.records.agent.BaseFragment
import it.facile.records.agent.R
import it.facile.records.agent.databinding.RecordDetailFragmentBinding
import it.facile.records.agent.recordslist.RecordsRecyclerAdapter

@AndroidEntryPoint
class RecordDetailFragment : BaseFragment(R.layout.record_detail_fragment) {
    private val args: RecordDetailFragmentArgs by navArgs()

    private val detailViewModel: RecordDetailViewModel by activityViewModels()

    private var fragmentBindings: RecordDetailFragmentBinding? = null
    private val binding get() = fragmentBindings!!

    private lateinit var listOfFile: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recordsListAdapter = RecordsRecyclerAdapter()

        listOfFile = binding.fileListRecycler
        listOfFile.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recordsListAdapter
        }
        detailViewModel.fetchRecordDetail(args.recordId)

        val addCallback = requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(R.id.beersListFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, addCallback)


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
}