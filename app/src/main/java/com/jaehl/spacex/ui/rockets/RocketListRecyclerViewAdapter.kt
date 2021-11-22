package com.jaehl.spacex.ui.rockets

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jaehl.spacex.databinding.ItemRocketListBinding


class RocketListRecyclerViewAdapter(
    val listener: OnItemClickListener
) : RecyclerView.Adapter<RocketListRecyclerViewAdapter.ViewHolder>() {

    private var items: List<RocketViewData> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRocketListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<RocketViewData>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemRocketListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RocketViewData) {
            binding.rocket = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: RocketViewData)
    }
}