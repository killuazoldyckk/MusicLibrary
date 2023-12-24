// HomeFragment.kt
package com.example.spotify.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spotify.adapter.Popular
import com.example.spotify.adapter.PopularAdapter
import com.example.spotify.api.ApiConfig
import com.example.spotify.api.ApiResponse
import com.example.spotify.api.DataItem
import com.example.spotify.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var rvpopular: RecyclerView
    private lateinit var popularAdapter: PopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

// Initialize RecyclerView
        rvpopular = binding.rvPopular
//        rvpopular.setHasFixedSize(true)

        // Set up RecyclerView layout manager
        val layoutManager = GridLayoutManager(requireContext(), 2)

        rvpopular.layoutManager = layoutManager

        // Set up RecyclerView adapter
        val listPopular = ArrayList<Popular>()
        popularAdapter = PopularAdapter(listPopular)
        rvpopular.adapter = popularAdapter

        // Fetch data from API and update the adapter
        fetchDataFromApi()

        return binding.root
    }


    private fun fetchDataFromApi() {
        val apiService = ApiConfig.getApiService()
        val call = apiService.getTop50Artist()

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    apiResponse?.let {
                        val listPopular = it.data?.map { dataItem ->
                            Log.d("DataItem", "Name: ${dataItem?.name}")

                            Popular(dataItem?.name.orEmpty(), getImageResourceId(dataItem?.pictureBig))
                        } ?: emptyList()
                        updateAdapter(listPopular)
                    }
                } else {
                    // Handle API error
                    Log.e("HomeFragment", "API call failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Handle network failure
                Log.e("HomeFragment", "Network call failed: ${t.message}")
            }
        })
    }


    private fun updateAdapter(listPopular: List<Popular>) {
        Log.d("HomeFragment", "Received ${listPopular.size} items from API")

        popularAdapter.setData(listPopular)
        popularAdapter.notifyDataSetChanged()
    }

    private fun getImageResourceId(url: String?): String {
        // For simplicity, just return the URL
        return url ?: ""
    }

}
