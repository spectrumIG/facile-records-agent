package it.facile.records.agent.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import it.facile.records.agent.beerslist.BeersListViewModel
import it.facile.records.agent.databinding.SearchBottomDialogBinding
import java.net.URLEncoder

class SearchFilterBottomDialog : BottomSheetDialogFragment() {
    private var binding: SearchBottomDialogBinding? = null

    private val viewModel: BeersListViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SearchBottomDialogBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchBrewedToFilter = binding?.searchBrewedAfter
        val searchBrewedFromFilter = binding?.searchBrewedBefore
        val brewedToEditText = binding?.brewedBefore
        val brewedFromEditText = binding?.brewedAfter
        val searchButton = binding?.searchButton
        val closeButton = binding?.searchFilterCloseButton

        searchBrewedToFilter?.editText!!.doOnTextChanged { text, _, _, _ ->
            if(!text!!.matches("(0?[1-9]|1[012])-(19|20)[0-9][0-9]".toRegex())) {
                searchBrewedToFilter.error = "Formato errato (mm-aaaa)"
            } else {
                searchBrewedToFilter.error = null
                searchButton?.isEnabled = text.isNotEmpty()
            }
        }
        searchBrewedFromFilter?.editText!!.doOnTextChanged { text, _, _, _ ->
            if(!text!!.matches("(0?[1-9]|1[012])-(19|20)[0-9][0-9]".toRegex())) {
                searchBrewedFromFilter.error = "Formato errato (mm-aaaa)"
            } else {
                searchBrewedFromFilter.error = null
                searchButton?.isEnabled = text.isNotEmpty()
            }
        }

        closeButton?.setOnClickListener {
            dismiss()
        }
        searchButton?.setOnClickListener {
            viewModel.filterFrom.value = brewedFromEditText?.text.toString()
            viewModel.filterTo.value = brewedToEditText?.text.toString()
            viewModel._isfiltered.value = brewedToEditText?.text!!.isNotEmpty() || brewedFromEditText?.text!!.isNotEmpty()

            viewModel.fetchBeersPaginatedAndWithFilterDate(
                page = 1,
                URLEncoder.encode(brewedToEditText.text.toString(), "utf-8"),
                URLEncoder.encode(brewedFromEditText?.text.toString(), "utf-8")
            )

            dismiss()
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}