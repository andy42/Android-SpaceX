package com.jaehl.spacex.ui.launches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jaehl.spacex.databinding.FragmentLaunchListBinding
import com.jaehl.spacex.ui.extensions.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchListFragment : Fragment(), LaunchRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: LaunchListViewModel by viewModels()
    private val adapter = LaunchRecyclerViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onError.observe(this, {
            context?.showErrorDialog(it)
        })
        viewModel.items.observe(this, {
            adapter.setItems(it)
        })

        viewModel.loading.observe(this, {

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLaunchListBinding.inflate(inflater, container, false)
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onItemClick(item: LaunchViewData) {
        //TODO add nav action
    }
}