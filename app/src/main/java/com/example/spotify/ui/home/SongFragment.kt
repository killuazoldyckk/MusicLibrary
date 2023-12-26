package com.example.spotify.ui.home

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spotify.adapter.Contributor
import com.example.spotify.adapter.Song
import com.example.spotify.adapter.SongAdapter
import java.util.concurrent.ExecutorService
import com.example.spotify.api.ApiConfig
import com.example.spotify.api.ContributorsItem
import com.example.spotify.api.TopSongsResponse
import com.example.spotify.database.FavouriteSong
import com.example.spotify.database.FavouriteSongDao
import com.example.spotify.database.FavouriteSongRoomDatabase

import com.example.spotify.databinding.FragmentSongBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executors

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.await



class SongFragment : Fragment(),SongAdapter.OnFavoriteClickListener {
    // TODO: Rename and change types of parameters
    private lateinit var binding : FragmentSongBinding
    private lateinit var songRv: RecyclerView
    private lateinit var songAdapter: SongAdapter
    private lateinit var favouriteSongDao: FavouriteSongDao
    private val listSong = ArrayList<Song>()
    private lateinit var mediaPlayer: MediaPlayer

    // Define artistId as a class-level property
    private var artistId: Int = 0
    private var isMediaPlayerPlaying = false
    private var currentPlayingPosition: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize favouriteSongDao here, you may obtain it from your Room database instance
        favouriteSongDao = FavouriteSongRoomDatabase.getDatabase(requireContext()).favouriteSongDao()
        // Initialize media player
        mediaPlayer = MediaPlayer()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSongBinding.inflate(inflater, container, false)


        // Retrieve arguments
        val artistName = arguments?.getString("name")?:""
        val artistId = arguments?.getInt("artistId", 0) ?: 0
        val photoUrlBig = arguments?.getString("photoUrlBig") ?: ""

        loadImage(photoUrlBig)

        binding.singerName.text = artistName

        // Initialize RecyclerView
        songRv = binding.songRv
        songRv.setHasFixedSize(true)

        // Set up RecyclerView layout manager
        val layoutManager = GridLayoutManager(requireContext(), 1)

        songRv.layoutManager = layoutManager

        // Set up RecyclerView adapter
        val listSong = ArrayList<Song>()

        // Set up RecyclerView adapter
        songAdapter = SongAdapter(listSong,songRv).apply {
            setOnFavoriteClickListener(this@SongFragment)
        }

        fetchDataFromApi(artistId)

        songRv.adapter = songAdapter

        binding.backButton.setOnClickListener{
            findNavController().navigateUp()
        }

        // Add an OnBackPressedCallback to handle system back button press
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        songAdapter.setOnPlayClickListener(object : SongAdapter.OnPlayClickListener {
            override fun onPlayClick(position: Int) {
                handlePlayButtonClick(position)
            }
        })


        return binding.root
    }

    private fun handlePlayButtonClick(position: Int) {
        if (position >= 0 && position < listSong.size) {
            if (currentPlayingPosition == position) {
                // If the same song is clicked again, toggle play/pause
                if (isMediaPlayerPlaying) {
                    mediaPlayer.pause()
                    updatePlayButtonState(position, false) // Update the play button state
                } else {
                    mediaPlayer.start()
                    updatePlayButtonState(position, true) // Update the play button state
                }
            } else {
                // If a different song is clicked, stop the current playback
                currentPlayingPosition?.let {
                    stopMediaPlayer()
                }

                // Start playing the new song
                startMediaPlayer(position)
            }
        }
    }
    private fun loadImage(url: String) {
        Glide.with(this)
            .load(url)
            .into(binding.imageView2)
    }
    private fun fetchDataFromApi(id: Int) {
        binding.loadingProgressBar.visibility = View.VISIBLE

        val apiService = ApiConfig.getApiService()
        val call = apiService.getTop10Songs(id)

        call.enqueue(object : Callback<TopSongsResponse> {
            override fun onResponse(call: Call<TopSongsResponse>, response: Response<TopSongsResponse>) {
                if (response.isSuccessful) {
                    val topSongsResponse = response.body()
                    topSongsResponse?.let {
                        val dataItems = it.data

                        // Map dataItems to Song list
                        val updatedListSong = dataItems?.map { dataItem ->
                            Song(
                                name = dataItem?.artist?.name.orEmpty(),
                                albumCover = dataItem?.album?.coverSmall.orEmpty(),
                                songName = dataItem?.title.orEmpty(),
                                preview = dataItem?.preview.orEmpty(),
                                contributors = mapContributors(dataItem?.contributors),
                            )
                        } ?: emptyList()

                        // Use a coroutine to update the adapter on the main thread
                        GlobalScope.launch(Dispatchers.Main) {
                            updateAdapter(updatedListSong)
                        }

                        // Perform the database operation using coroutine in the background
                        GlobalScope.launch(Dispatchers.IO) {
                            updatedListSong.forEach { song ->
                                song.isFavorite = isSongFavoriteLocally(song.songName)
                            }

                            // Use a coroutine to update the adapter on the main thread
                            launch(Dispatchers.Main) {
                                updateAdapter(updatedListSong)
                            }

                            // Update the outer listSong with the new data
                            listSong.clear()
                            listSong.addAll(updatedListSong)
                        }

                    }
                } else {
                    // Handle API error
                    Log.e("SongFragment", "API call failed: ${response.message()}")
                }

                binding.loadingProgressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<TopSongsResponse>, t: Throwable) {
                // Handle network failure
                Log.e("SongFragment", "Network call failed: ${t.message}")
                binding.loadingProgressBar.visibility = View.GONE
            }
        })
    }
    private fun updateAdapter(listSong: List<Song>) {
        if (!songRv.isComputingLayout) {
            Log.d("SongFragment", "Received ${listSong.size} items from API")

            songAdapter.setData(listSong)
            songAdapter.notifyDataSetChanged()
        } else {
            // Retry the update if the layout is still computing
            fetchDataFromApi(artistId)
        }
    }
    private fun isSongFavoriteLocally(songName: String): Boolean {
        // Use FavouriteSongDao to check if the song is in the local favorites
        return favouriteSongDao.isSongFavourite(songName) != null
    }
    private fun mapContributors(contributors: List<ContributorsItem?>?): List<Contributor> {
        return contributors?.mapNotNull { contributor ->
            Contributor(name = contributor?.name.orEmpty())
        } ?: emptyList()
    }
    override fun onFavoriteClick(position: Int) {
        Log.d("SongFragment", "Favorite Clicked at position $position, List size: ${listSong.size}")
        if (position >= 0 && position < listSong.size) {
            // Handle favorite button click here
            val song = listSong[position]

            // Toggle the favorite state
            song.isFavorite = !song.isFavorite

            // Update the UI on the main thread
            lifecycleScope.launch(Dispatchers.Main) {
                // Notify the adapter about the change in the favorite state
                songAdapter.setFavoriteState(position, song.isFavorite)
                songAdapter.notifyItemChanged(position)
            }

            if (!song.isFavorite) {
                // If the song is not marked as a favorite, save it to the database
                saveToFavoriteDatabase(song)
                songAdapter.setFavoriteState(position, true)
            } else {
                songAdapter.setFavoriteState(position, false)
                removeFromFavoriteDatabase(song)
                // If the song is already marked as a favorite, you can handle any specific logic here
                Log.d("SongFragment", "Song is already marked as a favorite")
            }
        } else {
            Log.e("SongFragment", "Invalid position: $position")
        }
    }
    private fun removeFromFavoriteDatabase(song: Song) {
        // Perform the database operation using coroutine in the background
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Delete the song from the FavouriteSong database using FavouriteSongDao
                favouriteSongDao.deleteSong(song.songName)
                Log.d("RemoveFromDatabase", "Successfully removed from the database")
            } catch (e: Exception) {
                Log.e(
                    "RemoveFromDatabase",
                    "Error removing from the database: ${e.javaClass.simpleName} - ${e.message}"
                )
            }
        }
    }
    private fun saveToFavoriteDatabase(song: Song) {
        // Convert contributors List to a comma-separated string for simplicity
        val contributors = song.contributors.map { it.name }.joinToString(", ")

        Log.d("SaveToDatabase", "Contributors: $contributors")

        // Create a FavouriteSong entity
        val favouriteSong = FavouriteSong(
            songName = song.songName,
            albumCover = song.albumCover,
            contributors = contributors,
            favourite = song.isFavorite
        )

        // Perform the database operation using coroutine in the background
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Save to the FavouriteSong database using FavouriteSongDao
                favouriteSongDao.insert(favouriteSong)
                Log.d("SaveToDatabase", "Successfully saved to the database")
            } catch (e: Exception) {
                Log.e(
                    "SaveToDatabase",
                    "Error saving to the database: ${e.javaClass.simpleName} - ${e.message}"
                )
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        // Stop the media player when the fragment is destroyed
        stopMediaPlayer()
    }
    private fun stopMediaPlayer() {
        mediaPlayer.reset()
        // Update the UI to reflect that no song is currently playing
        currentPlayingPosition?.let {
            updatePlayButtonState(it, false)
        }
        currentPlayingPosition = null
    }
    private fun updatePlayButtonState(position: Int, isPlaying: Boolean) {
        currentPlayingPosition = position
        isMediaPlayerPlaying = isPlaying
        songAdapter.setPlayState(position, isPlaying)
        songAdapter.notifyDataSetChanged()
    }
    private fun startMediaPlayer(position: Int) {
        val song = listSong[position]
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(song.preview)
            mediaPlayer.prepare()
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                stopMediaPlayer()
            }

            // Update the UI to reflect the currently playing song
            updatePlayButtonState(position, true)
            songAdapter.setPlayState(position, true) // Corrected method name
        } catch (e: IOException) {
            Log.e("SongFragment", "Error playing song: ${e.message}")
        }
    }

}

