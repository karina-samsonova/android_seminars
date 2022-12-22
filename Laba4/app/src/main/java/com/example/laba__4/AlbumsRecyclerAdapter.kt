package com.example.laba__4

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class AlbumsRecyclerAdapter(private val info: Array<Album>) :
    RecyclerView.Adapter<AlbumsRecyclerAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView
        val title: TextView
        val count: TextView

        init{
            image = itemView.findViewById(R.id.thumb)
            title = itemView.findViewById(R.id.name)
            count = itemView.findViewById(R.id.count)

            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_albums, parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val a: Album = info[position]
        if (a.title.length > 15){
            holder.title.text = a.title.substring(0, 15) + "..."
        } else {
            holder.title.text = a.title
        }

        val str = if (a.size == "1") {
            a.size + " photo"
        } else {
            a.size + " photos"
        }
        holder.count.text = str

        holder.image.setImageBitmap(a.thumb)

    }

    override fun getItemCount() = info.size
}
