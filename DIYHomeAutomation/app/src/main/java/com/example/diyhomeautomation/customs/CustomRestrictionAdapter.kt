package com.example.diyhomeautomation.customs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Switch
import android.widget.TextView
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.models.Restriction

/**
 * Custom adapter for displaying a list of restrictions in the app.
 *
 * @param context The context of the application.
 * @param resource The layout resource ID for each item in the list.
 * @param objects The list of restrictions to be displayed.
 */
class CustomRestrictionAdapter(
    context: Context,
    resource: Int,
    objects: MutableList<Restriction>
) :
    ArrayAdapter<Restriction>(context, resource, objects) {

    private var mContext: Context
    private var mValues: MutableList<Restriction>
    private var mResource: Int

    /**
     * Callback function to be invoked when an item is clicked.
     */
    var onItemClick: ((String) -> Unit)? = null

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
            // If convertView is null, inflate the layout and create a new ViewHolder
            view = LayoutInflater.from(mContext).inflate(mResource, parent, false)
            view.tag = MyViewHolder(view)
        }

        // Get the ViewHolder from the recycled view or the newly created view
        val vh: MyViewHolder = view.tag as MyViewHolder
        val value = mValues[position]

        // Set data to the ViewHolder
        vh.title?.text = value.name
        vh.isActive?.isChecked = position % 2 == 1

        // Set a click listener for the item
        view.setOnClickListener {
            onItemClick?.invoke(value.name)
        }

        return view
    }

    /**
     * ViewHolder pattern to improve ListView performance by recycling views.
     *
     * @param view The view to be held by the ViewHolder.
     */
    private class MyViewHolder(view: View?) {
        val title = view?.findViewById<TextView>(R.id.cardRestriction_title_tv)
        val total = view?.findViewById<TextView>(R.id.cardRestriction_total_tv)
        val isActive = view?.findViewById<Switch>(R.id.cardRestriction_active_sw)
    }
}
