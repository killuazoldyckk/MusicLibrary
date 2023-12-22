package com.example.spotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat

class PopularAdapter(private val listPopular: ArrayList<Popular>) : RecyclerView.Adapter<PopularAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview, parent, false)
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (penyanyi, photo) = listPopular[position]

        holder.textView1.text = penyanyi
        holder.imageView1.setImageResource(photo)

        val backgroundColorResId = when (position % 6) {
            0 -> R.color.blue
            1 -> R.color.brown
            2 -> R.color.green
            3 -> R.color.redpink
            4 -> R.color.red
            else -> R.color.purple
        }
        val backgroundColor = ContextCompat.getColor(holder.itemView.context, backgroundColorResId)
        holder.itemView.setBackgroundColor(backgroundColor)

    }



    override fun getItemCount(): Int = listPopular.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView1: ImageView = itemView.findViewById(R.id.imageView)
        val textView1: TextView = itemView.findViewById(R.id.textView)
    }

}
