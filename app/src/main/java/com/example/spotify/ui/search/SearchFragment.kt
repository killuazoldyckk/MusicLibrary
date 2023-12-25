package com.example.spotify.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spotify.R
import com.example.spotify.adapter.Popular
import com.example.spotify.adapter.PopularAdapter
import com.example.spotify.api.ApiConfig
import com.example.spotify.api.ApiResponse
import com.example.spotify.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var rvsearch: RecyclerView
    private lateinit var popularAdapter: PopularAdapter
    private var originalList: List<Popular> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize RecyclerView
        rvsearch = binding.rvSearch
        rvsearch.setHasFixedSize(true)

        // Set up RecyclerView layout manager
        val layoutManager = GridLayoutManager(requireContext(), 2)

        rvsearch.layoutManager = layoutManager

        // Set up RecyclerView adapter
        val listPopular = ArrayList<Popular>()
        popularAdapter = PopularAdapter(listPopular).apply {
            setOnItemClickListener(object : PopularAdapter.OnItemClickListener {
                override fun onItemClick(id: Int, name: String, photoUrlBig: String) {
                    // Handle item click, e.g., navigate to SongFragment
                    navigateToSongFragment(id, name, photoUrlBig)
                }
            })
        }


        rvsearch.adapter = popularAdapter

        fetchDataFromApi()

        // Set up SearchView listener
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // If needed, you can make an API call here
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SearchFragment", "onQueryTextChange: $newText")
                newText?.let { filterList(it) }
                return true
            }
        })

        return view
    }
    private fun filterList(query: String) {
        // Filter the original list based on the search query
        val filteredList = originalList.filter { popular ->
            popular.name.toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))
        }

        // Update the adapter with the filtered list
        updateAdapter(filteredList)
    }


    private fun fetchDataFromApi() {
        Log.d("SearchFragment", "Fetching data from API")

        val apiService = ApiConfig.getApiService()
        val call = apiService.getTop10Artist()

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    apiResponse?.let {
                        val listPopular = it.data?.map { dataItem ->
                            Log.d("DataItem", "Name: ${dataItem?.name}")
                            Popular(
                                dataItem?.id ?: 0,
                                dataItem?.name.orEmpty(),
                                getImageResourceId(dataItem?.pictureMedium),
                                getImageResourceId(dataItem?.pictureBig),
                            )                        } ?: emptyList()
                        originalList = listPopular
                        // Initially, show the full list
                        updateAdapter(originalList)
                    }
                } else {
                    // Handle API error
                    Log.e("SearchFragment", "API call failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Handle network failure
                Log.e("SearchFragment", "Network call failed: ${t.message}")
            }
        })
    }



    private fun updateAdapter(listPopular: List<Popular>) {
        Log.d("SearchFragment", "Received ${listPopular.size} items from API")

        popularAdapter.setData(listPopular)
        popularAdapter.notifyDataSetChanged()
    }

    private fun getImageResourceId(url: String?): String {
        // For simplicity, just return the URL
        return url ?: ""
    }

    private fun navigateToSongFragment(id: Int, name: String, photoUrlBig: String) {

        // Create a bundle to pass data to SongFragment
        val bundle = Bundle().apply {
            putInt("artistId", id)
            putString("photoUrlBig", photoUrlBig)
            putString("name", name)
        }

        // Use NavController to navigate to SongFragment
        findNavController().navigate(R.id.navigation_song, bundle)
    }
}