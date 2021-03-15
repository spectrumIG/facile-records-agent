package it.facile.records.agent.beerdetail

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import it.facile.records.agent.BaseFragment
import it.facile.records.agent.R
import it.facile.records.agent.databinding.BeerDetailFragmentBinding
import timber.log.Timber

@AndroidEntryPoint
class BeerDetailFragment : BaseFragment(R.layout.beer_detail_fragment) {
    private val args: BeerDetailFragmentArgs by navArgs()

    private val detailViewModel: BeerDetailViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val detailBindings = DataBindingUtil.inflate<BeerDetailFragmentBinding>(
            inflater,
            R.layout.beer_detail_fragment,
            container,
            false)
            .apply {
                viewModel = detailViewModel
                lifecycleOwner = viewLifecycleOwner
            }

        return detailBindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addCallback = requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(R.id.beersListFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, addCallback)

        Timber.d("Birra scelta con id = ${args.beerId}")

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