package com.emman.android.asteroidradar.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.emman.android.asteroidradar.databinding.FragmentMenuBinding
import com.emman.android.asteroidradar.presentation.adapter.AsteroidAdapter
import com.emman.android.asteroidradar.presentation.adapter.AsteroidListener
import com.emman.android.asteroidradar.presentation.viewmodels.MenuViewModel

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private val viewModel: MenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AsteroidAdapter(
            AsteroidListener { asteroid ->
                findNavController().navigate(
                    MenuFragmentDirections.actionMenuFragmentToDetailFragment(
                        asteroid.id
                    )
                )
            }
        )

        binding.asteroidRecycler.adapter = adapter

        viewModel.asteroids.observe(viewLifecycleOwner) { asteroids ->
            adapter.submitList(asteroids)
        }

        viewModel.pictureOfTheDay.observe(viewLifecycleOwner) { pictureDay ->
            binding.pictureDay = pictureDay
        }

        viewModel.loadData()
        viewModel.loadImageOfTheDay()

    }
}