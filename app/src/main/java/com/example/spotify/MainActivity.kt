package com.example.spotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvpopular: RecyclerView
    private val list = ArrayList<Popular>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi RecyclerView
        rvpopular = findViewById(R.id.rv_popular)
        rvpopular.setHasFixedSize(true)

        // Mengisi data ke dalam list
        list.addAll(getListPopular())
        val layoutManager = GridLayoutManager(this, 2) // Set 2 columns
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                // Set span size 2 for Taylor Swift and Bruno Mars, and 1 for others
                return if (position == 9 || position == 6) 2 else 1
            }
        }
        rvpopular.layoutManager = layoutManager

        val popularAdapter = PopularAdapter(list)
        rvpopular.adapter = popularAdapter



    }




    private fun getListPopular(): ArrayList<Popular> {
        val dataPenyanyi = resources.getStringArray(R.array.data_penyanyi)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listPopular = ArrayList<Popular>()

        for (i in 0 until dataPhoto.length()) {
            val popular = Popular(dataPenyanyi[i], dataPhoto.getResourceId(i, -1))
            listPopular.add(popular)
        }
        dataPhoto.recycle()
        return listPopular
    }
}