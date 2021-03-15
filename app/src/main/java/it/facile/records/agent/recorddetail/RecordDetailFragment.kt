package it.facile.records.agent.recorddetail

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import it.facile.records.agent.BaseFragment
import it.facile.records.agent.R

@AndroidEntryPoint
class RecordDetailFragment : BaseFragment(R.layout.record_detail_fragment) {
    private val args: RecordDetailFragmentArgs by navArgs()

    private val detailViewModel: RecordDetailViewModel by activityViewModels()

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//
//        val detailBindings = DataBindingUtil.inflate<RecordDetailFragmentBinding>(
//            inflater,
//            R.layout.record_detail_fragment,
//            container,
//            false)
//            .apply {
//                viewModel = detailViewModel
//                lifecycleOwner = viewLifecycleOwner
//            }
//
//        return detailBindings.root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addCallback = requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(R.id.beersListFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, addCallback)

        detailViewModel.fetchBeerDetail(args.beerId)
        detailViewModel.showError.observe(viewLifecycleOwner) { show ->
            if(show) {
              AlertDialog.Builder(requireContext())
                  .setMessage(getString(R.string.alert_error_message))
                  .setTitle(getString(R.string.error_alert_title))
                  .setPositiveButton(android.R.string.ok) { dialog, _ ->
                      dialog.dismiss()
                      findNavController().navigate(R.id.beersListFragment)
                  }
                  .create().show()
            }
        }

    }
}