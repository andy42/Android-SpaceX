package com.jaehl.spacex.ui.launches

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jaehl.spacex.databinding.ItemLaunchListBinding


class LaunchRecyclerViewAdapter(
    val listener: OnItemClickListener
) : RecyclerView.Adapter<LaunchRecyclerViewAdapter.ViewHolder>() {

    private var items: List<LaunchViewData> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLaunchListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<LaunchViewData>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemLaunchListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LaunchViewData) {
            binding.launch = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: LaunchViewData)
    }
}