package com.example.spotify.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spotify.adapter.FavouriteSongAdapter
//import com.example.spotify.adapter.FavouriteSongAdapter
import com.example.spotify.databinding.FragmentLibraryBinding
import com.example.spotify.helper.ViewModelFactory
import com.example.spotify.repository.FavouriteSongRepository

class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryBinding
    private lateinit var viewModel: LibraryViewModel
    private val favouriteSongAdapter = FavouriteSongAdapter()
    private lateinit var viewModelFactory: ViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val repository = FavouriteSongRepository(application)

        // Use the existing ViewModelFactory
        viewModelFactory = ViewModelFactory.getInstance(requireNotNull(this.activity).application,repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LibraryViewModel::class.java)

        setupRecyclerView()

        viewModel.getAllFavouriteSongs().observe(viewLifecycleOwner) { favouriteSongs ->
            favouriteSongAdapter.setListFavouriteSong(favouriteSongs)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvFavourite.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favouriteSongAdapter
            // Add any additional settings for your RecyclerView
        }
    }
}
