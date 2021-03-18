package it.facile.records.agent.recordslist

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    private var fragmentBindings: RecordsListFragmentBinding? = null
    private val binding get() = fragmentBindings!!

    private val viewModel: RecordsListViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView

    private val swipeRefreshLayout: SwipeRefreshLayout
        get() {
            return binding.listMainRefreshContainer
        }

    private val emptyListMessage: TextView
        get() {
            return binding.noItemText
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBindings = RecordsListFragmentBinding.bind(view)

        recyclerView = binding.mainRecordsRecycler
        val recordsListAdapter = RecordsRecyclerAdapter()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recordsListAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            recordsListAdapter.clearData()
            viewModel.getAllRecords()
        }

        viewModel.showProgress.observe(viewLifecycleOwner) { show ->
            if(show) {
                emptyListMessage.visibility = View.GONE
            }
            swipeRefreshLayout.isRefreshing = show
        }

        viewModel.records.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Result.Success -> {
                    enableList()
                    if(resource.data.isEmpty()) {
                        enableErrorMessage()
                    } else {
                        recordsListAdapter.setData(resource.data)
                    }
                }

                is Result.Error -> {
                    enableErrorMessage()
                }
                Result.Loading -> swipeRefreshLayout.isRefreshing = true
            }
        }
    }

    private fun enableErrorMessage() {
        recyclerView.visibility = View.GONE
        swipeRefreshLayout.visibility = View.VISIBLE
        emptyListMessage.visibility = View.VISIBLE
        Snackbar.make(binding.constrainMainLayout, getString(R.string.main_list_error_msg), Snackbar.LENGTH_LONG).show()
    }

    private fun enableList() {
        swipeRefreshLayout.isRefreshing = false
        binding.mainRecordsRecycler.visibility = View.VISIBLE
        emptyListMessage.visibility = View.GONE
    }

    override fun onDestroyView() {
        fragmentBindings = null
        super.onDestroyView()
    }
}
