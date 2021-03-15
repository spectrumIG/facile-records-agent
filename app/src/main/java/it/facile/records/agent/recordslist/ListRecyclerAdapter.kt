package it.facile.records.agent.recordslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.facile.records.agent.databinding.BeerItemBinding
import it.facile.records.agent.domain.entity.local.BeerForUi

class BeersLinearRecyclerAdapter :
    ListAdapter<BeerForUi,BeersLinearRecyclerAdapter.ViewHolder>(BeersDiffCallback()) {

    private var data: List<BeerForUi> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(BeerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun setData(data: List<BeerForUi>) {
        (this.data as ArrayList).addAll(data)
        notifyDataSetChanged()
    }

    fun clearData() {
        (this.data as ArrayList).clear()
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: BeerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: BeerForUi,
        ) {
            with(itemView) {
                binding.beerName.text = item.name
                binding.beerTagLine.text = item.tagline
                binding.beerFirstBrewd.text = item.date
                binding.beerItemContainer.setOnClickListener {
                    val toDetail = RecordsListFragmentDirections.actionBeersListFragmentToBeerDetailFragment(item.id)
                    findNavController().navigate(toDetail)
                }
            }
        }
    }
}

private class BeersDiffCallback : DiffUtil.ItemCallback<BeerForUi>() {
    override fun areItemsTheSame(oldItem: BeerForUi, newItem: BeerForUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BeerForUi, newItem: BeerForUi): Boolean {
        return oldItem == newItem
    }

}