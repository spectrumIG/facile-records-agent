package it.facile.records.agent.recordslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import it.facile.records.agent.BaseFragment
import it.facile.records.agent.R
import it.facile.records.agent.databinding.RecordsListFragmentBinding
import it.facile.records.agent.library.android.entity.Result

/**
 * Fragmment that manages the Grid Recycler and part of pagination
 *
 * */
@AndroidEntryPoint
class RecordsListFragment : BaseFragment(R.layout.records_list_fragment) {
    var fragmentBindings: RecordsListFragmentBinding? = null

    private val viewModel: RecordsListViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBindings = RecordsListFragmentBinding.bind(view)

        recyclerView = fragmentBindings!!.mainBeersRecycler

        val beersListAdapter = BeersLinearRecyclerAdapter()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = beersListAdapter
        }

        viewModel.getAllRecords()

        val listMainRefreshContainer = fragmentBindings!!.listMainRefreshContainer

        listMainRefreshContainer.setOnRefreshListener {
            viewModel.getAllRecords()
        }

        viewModel.showProgress.observe(viewLifecycleOwner) { show ->
            if(show) {
                fragmentBindings!!.progressBar.visibility = View.VISIBLE
                fragmentBindings!!.noItemText.visibility = View.GONE

            } else {
                fragmentBindings!!.progressBar.visibility = View.GONE
            }
            listMainRefreshContainer.isRefreshing = show
        }

        viewModel.beers.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Result.Success -> {
                    enableList()
                    if(resource.data.isEmpty()) {
                        enableErrorMessage()
                    } else {
                        beersListAdapter.setData(resource.data)
                    }
                }

                is Result.Error -> {
                    enableErrorMessage()
                }
            }
        }
    }

    private fun enableErrorMessage() {
        fragmentBindings!!.mainBeersRecycler.visibility = View.GONE
        fragmentBindings!!.listMainRefreshContainer.visibility = View.VISIBLE
        fragmentBindings!!.noItemText.visibility = View.VISIBLE
        Snackbar.make(fragmentBindings!!.root,getString(R.string.main_list_error_msg),Snackbar.LENGTH_LONG) //TODO FIX This message
    }

    private fun enableList() {
        fragmentBindings!!.mainBeersRecycler.visibility = View.VISIBLE
        fragmentBindings!!.noItemText.visibility = View.GONE
    }

    override fun onDestroyView() {
        fragmentBindings = null
        super.onDestroyView()
    }
}
