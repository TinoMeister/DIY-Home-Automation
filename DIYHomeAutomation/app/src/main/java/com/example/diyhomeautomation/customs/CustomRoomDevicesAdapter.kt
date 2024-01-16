package com.example.diyhomeautomation.customs

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.models.Device


/**
 * Custom RecyclerView adapter for displaying a list of devices in a room.
 *
 * @param objects The list of devices to be displayed.
 */
class CustomRoomDevicesAdapter(private val objects: MutableList<Device>) :
    RecyclerView.Adapter<CustomRoomDevicesAdapter.MyViewHolder>() {

    /**
     * Callback function to be invoked when an item is clicked.
     */
    var onItemClick: ((Device) -> Unit)? = null

    /**
     * ViewHolder class to hold views for each item in the RecyclerView.
     *
     * @param itemView The view for each item in the RecyclerView.
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.cardRoomDev_img)
        val title = itemView.findViewById<TextView>(R.id.cardRoomDev_title_tv)
    }

    /**
     * Overrides the onCreateViewHolder method to inflate the layout for each item.
     *
     * @param parent The parent view group.
     * @param viewType The type of view.
     * @return The ViewHolder for each item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_room_devices_cardview, parent, false)
        return MyViewHolder(view)
    }

    /**
     * Overrides the getItemCount method to return the number of items in the list.
     *
     * @return The number of items in the list.
     */
    override fun getItemCount(): Int {
        return objects.size
    }

    /**
     * Overrides the onBindViewHolder method to bind data to each item in the RecyclerView.
     *
     * @param holder The ViewHolder for each item.
     * @param position The position of the item in the list.
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = objects[position]
        holder.title.text = obj.name

        if (!obj.icon.isNullOrBlank()) {
            try {
                val resourceId = obj.icon!!.toInt()
                holder.img?.setImageResource(resourceId)
            } catch (e: NumberFormatException) {
                Log.e("", "", e)
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(obj)
        }
    }
}