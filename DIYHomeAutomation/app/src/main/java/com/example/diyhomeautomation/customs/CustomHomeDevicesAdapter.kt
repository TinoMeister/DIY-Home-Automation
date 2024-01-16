package com.example.diyhomeautomation.customs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.models.Device
import com.example.diyhomeautomation.models.Room

/**
 * Custom adapter for displaying a list of devices in the home screen.
 *
 * @param context The context of the application.
 * @param resource The layout resource ID for each item in the list.
 * @param objects The list of devices to be displayed.
 */
class CustomHomeDevicesAdapter(context: Context, resource: Int, objects: MutableList<Device>) :
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
        vh.isActive?.isChecked = position % 2 == 1
        // vh.body?.text = value.body

        return view
    }

    /**
     * ViewHolder pattern to improve ListView performance by recycling views.
     *
     * @param view The view to be held by the ViewHolder.
     */
    private class MyViewHolder(view: View?) {
        val img = view?.findViewById<ImageView>(R.id.cardHomeDev_img)
        val title = view?.findViewById<TextView>(R.id.cardHomeDev_title_tv)
        val isActive = view?.findViewById<Switch>(R.id.cardHomeDev_active_sw)
    }
}
