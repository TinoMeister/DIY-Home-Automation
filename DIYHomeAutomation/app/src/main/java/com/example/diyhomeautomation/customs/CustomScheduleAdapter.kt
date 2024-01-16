package com.example.diyhomeautomation.customs

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Switch
import android.widget.TextView
import com.example.diyhomeautomation.R

/**
 * Custom ArrayAdapter for displaying a list of schedule items.
 *
 * @param context The context of the application.
 * @param resource The resource ID for the layout file representing each schedule item.
 * @param objects The list of schedule items to be displayed.
 */
class CustomScheduleAdapter(context: Context, resource: Int, objects: MutableList<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    private var mContext: Context
    private var mValues: MutableList<String>
    private var mResource: Int

    /**
     * Callback function to be invoked when an item is clicked.
     */
    var onItemClick: ((String) -> Unit)? = null

    /**
     * Initializes the adapter with the provided context, resource ID, and list of schedule items.
     *
     * @param context The context of the application.
     * @param resource The resource ID for the layout file representing each schedule item.
     * @param objects The list of schedule items to be displayed.
     */
    init {
        mContext = context
        mValues = objects
        mResource = resource
    }

    /**
     * Overrides the getView method to create and return a view for each item in the list.
     *
     * @param position The position of the item in the list.
     * @param convertView The recycled view.
     * @param parent The parent view group.
     * @return The view for each item in the list.
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

        vh.title?.text = value
        vh.isActive?.isChecked = position % 2 == 1

        view.setOnClickListener {
            onItemClick?.invoke(value)
        }

        return view
    }

    /**
     * ViewHolder class to hold views for each schedule item.
     *
     * @param view The view for each schedule item.
     */
    private class MyViewHolder(view: View?) {
        val title = view?.findViewById<TextView>(R.id.cardSchedule_title_tv)
        val time = view?.findViewById<TextView>(R.id.cardSchedule_time_tv)
        val day = view?.findViewById<TextView>(R.id.cardSchedule_day_tv)
        val total = view?.findViewById<TextView>(R.id.cardSchedule_total_tv)
        val isActive = view?.findViewById<Switch>(R.id.cardSchedule_active_sw)
    }
}