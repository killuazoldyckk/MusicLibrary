// PopularAdapter.kt
package com.example.spotify.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spotify.R
import com.google.android.material.imageview.ShapeableImageView

class PopularAdapter(private val listPopular: ArrayList<Popular>) : RecyclerView.Adapter<PopularAdapter.ListViewHolder>() {

    private var listener: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview, parent, false)
        return ListViewHolder(view)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val popular = listPopular[position]

        Glide.with(holder.itemView)
            .load(popular.photoUrlMedium)
            .centerCrop()
            .into(holder.imageView1)

        holder.cardView.background = getBackgroundDrawable(holder.itemView.context, position)
        holder.textView1.text = popular.name

        holder.itemView.setOnClickListener {
            listener?.onItemClick(popular.id, popular.name, popular.photoUrlBig)
        }
    }

    private fun getBackgroundDrawable(context: Context, position: Int): Drawable {
        val backgrounds = arrayOf(
            R.drawable.gradient_background1,
            R.drawable.gradient_background2,
            R.drawable.gradient_background3,
            R.drawable.gradient_background4,
            R.drawable.gradient_background5
        )

        val index = position % backgrounds.size
        return ContextCompat.getDrawable(context, backgrounds[index])!!
    }

    override fun getItemCount(): Int = listPopular.size

    fun setData(newList: List<Popular>) {
        listPopular.clear()
        listPopular.addAll(newList)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView1: ShapeableImageView = itemView.findViewById(R.id.imageView)
        val textView1: TextView = itemView.findViewById(R.id.textView)
        val cardView: CardView = itemView.findViewById(R.id.cardView)

    }

    interface OnItemClickListener {
        fun onItemClick(id: Int, name: String, photoUrlBig: String)

    }
}
