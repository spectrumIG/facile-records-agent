package it.facile.records.agent.recordslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.facile.records.agent.databinding.RecordItemBinding
import it.facile.records.agent.domain.entity.local.RecordForUi

class RecordsRecyclerAdapter :
    ListAdapter<RecordForUi, RecordsRecyclerAdapter.ViewHolder>(BeersDiffCallback()) {

    private var data: List<RecordForUi> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun setData(data: List<RecordForUi>) {
        (this.data as ArrayList).addAll(data)
        notifyDataSetChanged()
    }

    fun clearData() {
        (this.data as ArrayList).clear()
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: RecordItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: RecordForUi,
        ) {
            with(itemView) {
                binding.recordName.text = item.recordName

//                binding.hasFileCheckImg.visibility = when {
//                    item.hasFile -> {
//                        View.VISIBLE
//                    }
//                    else -> {
//                        View.GONE
//                    }
//                }
                binding.recordItemContainer.setOnClickListener {
                    item.recordName?.let { name ->
                        RecordsListFragmentDirections.actionBeersListFragmentToBeerDetailFragment(item.id, name)
                    }.let { navDirections -> navDirections?.let { directions -> findNavController().navigate(directions) } }
                }
            }
        }
    }
}

private class BeersDiffCallback : DiffUtil.ItemCallback<RecordForUi>() {
    override fun areItemsTheSame(oldItem: RecordForUi, newItem: RecordForUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecordForUi, newItem: RecordForUi): Boolean {
        return oldItem == newItem
    }

}
