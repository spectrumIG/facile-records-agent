package it.facile.records.agent.recorddetail

import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.facile.records.agent.databinding.FileItemBinding
import it.facile.records.agent.domain.entity.local.FileOfRecordUI
import java.text.SimpleDateFormat

class FileForRecordListAdapter :
    ListAdapter<FileOfRecordUI, FileForRecordListAdapter.ViewHolder>(FileListDiffCallback()) {

    private var data: List<FileOfRecordUI?> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun setData(list: List<FileOfRecordUI?>) {
        clearData()
        data = list
        notifyDataSetChanged()
    }

    fun clearData() {
        (this.data as ArrayList).clear()
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: FileItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: FileOfRecordUI?,
        ) {
            with(itemView) {
                binding.fileNameLabel.text = item?.filename ?: ""
                binding.fileSizeLabel.text = item?.let { Formatter.formatShortFileSize(context, it.fileSize) }
                binding.dateSaveLabel.text = SimpleDateFormat.getDateInstance().format(item?.addingDate?.time!!)
            }
        }
    }
}

private class FileListDiffCallback : DiffUtil.ItemCallback<FileOfRecordUI>() {
    override fun areItemsTheSame(oldItem: FileOfRecordUI, newItem: FileOfRecordUI): Boolean {
        return oldItem.filename == newItem.filename
    }

    override fun areContentsTheSame(oldItem: FileOfRecordUI, newItem: FileOfRecordUI): Boolean {
        return oldItem == newItem
    }

}