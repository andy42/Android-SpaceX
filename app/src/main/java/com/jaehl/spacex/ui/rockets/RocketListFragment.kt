package com.jaehl.spacex.ui.rockets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jaehl.spacex.databinding.FragmentRocketListBinding
import com.jaehl.spacex.ui.extensions.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RocketListFragment : Fragment(), RocketListRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: RocketListViewModel by viewModels()
    private val adapter = RocketListRecyclerViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onError.observe(this){
            context?.showErrorDialog(it)
        }
        viewModel.items.observe(this) {
            adapter.setItems(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRocketListBinding.inflate(inflater, container, false)
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onItemClick(item: RocketViewData) {
        //TODO add nav action
    }
}