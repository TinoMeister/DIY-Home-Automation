package com.example.diyhomeautomation.customs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diyhomeautomation.R


class CustomRoomDevicesAdapter(private val objects: MutableList<String>) :
    RecyclerView.Adapter<CustomRoomDevicesAdapter.MyViewHolder>() {

    var onItemClick : ((String) -> Unit)? = null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.cardRoomDev_img)
        val title = itemView.findViewById<TextView>(R.id.cardRoomDev_title_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_room_devices_cardview,
           parent,
           false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return objects.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = objects[position]
        holder.title.text = obj

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(obj)
        }
    }
}