package com.example.spotify

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.spotify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)

        binding.bottomNav.setupWithNavController(navController)

        // Set up icon tint color programmatically
        val iconColorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(
                ContextCompat.getColor(this, R.color.selected_icon_color),
                ContextCompat.getColor(this, R.color.default_icon_color)
            )
        )

        binding.bottomNav.itemIconTintList = iconColorStateList
        binding.bottomNav.itemTextColor = iconColorStateList

        // Set up item background programmatically
        val unselectedBackground: Drawable? =
            ContextCompat.getDrawable(this, R.drawable.nav_item_unselected_item)
        ViewCompat.setBackground(binding.bottomNav, unselectedBackground)


    }

}