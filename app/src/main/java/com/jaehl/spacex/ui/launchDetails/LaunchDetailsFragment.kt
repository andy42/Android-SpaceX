package com.jaehl.spacex.ui.launchDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.jaehl.spacex.databinding.FragmentLaunchDetailsBinding
import com.jaehl.spacex.ui.extensions.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchDetailsFragment : Fragment() {

    private val args: LaunchDetailsFragmentArgs by navArgs()

    private val viewModel: LaunchDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLaunchDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onError.observe(this, {
            context?.showErrorDialog(it)
        })

        viewModel.loading.observe(this, {

        })

        viewModel.setLaunchId(args.launchId)
    }
}