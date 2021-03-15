package it.facile.records.agent.recordslist

import androidx.recyclerview.widget.RecyclerView
import it.facile.records.agent.widgets.EndlessRecyclerViewScrollListener

class BeersEndelessListScrollListener(
    layoutManager: RecyclerView.LayoutManager?,
    private val viewModel: RecordsListViewModel,
    private val isFiltered: Boolean,

    ) : EndlessRecyclerViewScrollListener(layoutManager) {

    var fromFilter: String = ""
    var toFilter: String = ""

    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
        if(isFiltered) {
            viewModel.fetchBeersPaginatedAndWithFilterDate(page, toFilter, fromFilter)
        } else {
            viewModel.fetchBeersPaginatedWith(page)
        }
    }
}
