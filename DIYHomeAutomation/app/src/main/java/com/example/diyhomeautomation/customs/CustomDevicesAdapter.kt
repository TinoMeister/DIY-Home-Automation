package com.example.diyhomeautomation.customs

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.models.Device

/**
 * Custom adapter for displaying a list of devices.
 *
 * @param context The context of the application.
 * @param resource The layout resource ID for each item in the list.
 * @param objects The list of devices to be displayed.
 */
class CustomDevicesAdapter(context: Context, resource: Int, objects: MutableList<Device>) :
    ArrayAdapter<Device>(context, resource, objects) {

    private var mContext: Context
    private var mValues: MutableList<Device>
    private var mResource: Int

    init {
        mContext = context
        mValues = objects
        mResource = resource
    }

    /**
     * Overrides the default getView method to customize the appearance of each item in the list.
     *
     * @param position The position of the item in the list.
     * @param convertView The recycled view to populate.
     * @param parent The parent view.
     * @return The custom view for the item at the specified position.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View

        if (convertView != null) {
            view = convertView
        } else {
            view = LayoutInflater.from(mContext).inflate(mResource, parent, false)
            view.tag = MyViewHolder(view)
        }

        val vh: MyViewHolder = view.tag as MyViewHolder
        val value = mValues[position]

        vh.title?.text = value.name
        vh.editBtn?.isActivated
        vh.deleteBtn?.isActivated

        if (!value.icon.isNullOrBlank()) {
            try {
                val resourceId = value.icon?.toIntOrNull()
                if (resourceId != null && resourceId != 0) {
                    vh.img?.setImageResource(resourceId)
                }
            } catch (e: NumberFormatException) {
                Log.e("CustomDevicesAdapter", "Invalid icon format", e)
            }
        }
        return view
    }

    /**
     * ViewHolder pattern to improve ListView performance by recycling views.
     *
     * @param view The view to be held by the ViewHolder.
     */
    private class MyViewHolder(view: View?) {
        val img = view?.findViewById<ImageView>(R.id.cardDev_img)
        val title = view?.findViewById<TextView>(R.id.cardDev_title_tv)
        val editBtn = view?.findViewById<Button>(R.id.cardDev_edit_btn)
        val deleteBtn = view?.findViewById<Button>(R.id.cardDev_delete_btn)
    }
}
