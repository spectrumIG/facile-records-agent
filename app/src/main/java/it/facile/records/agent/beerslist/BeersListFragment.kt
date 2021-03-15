package it.facile.records.agent.beerslist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import it.facile.records.agent.BaseFragment
import it.facile.records.agent.R
import it.facile.records.agent.databinding.BeersListFragmentBinding
import it.facile.records.agent.library.android.entity.Result
import it.facile.records.agent.widgets.SearchFilterBottomDialog
import timber.log.Timber

/**
 * Fragmment that manages the Grid Recycler and part of pagination
 *
 * */
@AndroidEntryPoint
class BeersListFragment : BaseFragment(R.layout.beers_list_fragment) {
    var fragmentBindings: BeersListFragmentBinding? = null

    private val viewModel: BeersListViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_search -> {
                Timber.d("SHOW DIALOG for search")
                SearchFilterBottomDialog().show(requireActivity().supportFragmentManager,"Search Dialog")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBindings = BeersListFragmentBinding.bind(view)

        recyclerView = fragmentBindings!!.mainBeersRecycler

        val beersListAdapter = BeersLinearRecyclerAdapter()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = beersListAdapter
        }

        viewModel.fetchBeersPaginatedWith(page)


        val listMainRefreshContainer = fragmentBindings!!.listMainRefreshContainer
        listMainRefreshContainer.setOnRefreshListener {
            viewModel.fetchBeersPaginatedWith(page)

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

        viewModel.filtered.observe(viewLifecycleOwner) { filtered ->
            if(filtered) {
                (recyclerView.adapter as BeersLinearRecyclerAdapter).clearData()
            }
            recyclerView.addOnScrollListener(BeersEndelessListScrollListener(recyclerView.layoutManager, viewModel, filtered).apply {
                this.fromFilter = viewModel.filterFrom.value!!
                this.toFilter = viewModel.filterTo.value!!
            })
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
