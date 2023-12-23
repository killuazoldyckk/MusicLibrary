// HomeFragment.kt
package com.example.spotify.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.spotify.adapter.PopularAdapter
import com.example.spotify.api.ApiConfig
import com.example.spotify.api.DataItem
import com.example.spotify.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // Fetch data using your API
        lifecycleScope.launch {
            val data = fetchData()
            (binding.rvPopular.adapter as? PopularAdapter)?.setData(data)
        }
    }

    private suspend fun fetchData(): List<DataItem> {
        return withContext(Dispatchers.IO) {
            // Make API call using Retrofit or any other networking library
            val response = ApiConfig.getApiService().getTop50Artist().execute()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvPopular.layoutManager = layoutManager

        // Set up the adapter
        val adapter = PopularAdapter(emptyList()) // Pass an empty list initially
        binding.rvPopular.adapter = adapter
    }
}
