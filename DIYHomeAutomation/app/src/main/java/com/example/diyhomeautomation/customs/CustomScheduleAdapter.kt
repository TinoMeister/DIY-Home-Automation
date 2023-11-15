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

class CustomScheduleAdapter(context: Context, resource: Int, objects: MutableList<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    private var mContext: Context
    private var mValues: MutableList<String>
    private var mResource: Int

    var onItemClick : ((String) -> Unit)? = null

    init {
        mContext = context
        mValues = objects
        mResource = resource
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View

        if (convertView != null) {
            view = convertView
        }
        else {
            view = LayoutInflater.from(mContext).inflate(mResource, parent, false)
            view.tag = MyViewHolder(view)
        }

        val vh: MyViewHolder = view.tag as MyViewHolder
        val value = mValues[position]

        vh.title?.text = value
        vh.isActive?.isChecked = position % 2 == 1
        //vh.body?.text = value.body

        view.setOnClickListener {
            onItemClick?.invoke(value)
        }

        return view
    }


    private class MyViewHolder(view: View?) {
        val title = view?.findViewById<TextView>(R.id.cardSchedule_title_tv)
        val time = view?.findViewById<TextView>(R.id.cardSchedule_time_tv)
        val day = view?.findViewById<TextView>(R.id.cardSchedule_day_tv)
        val total = view?.findViewById<TextView>(R.id.cardSchedule_total_tv)
        val isActive = view?.findViewById<Switch>(R.id.cardSchedule_active_sw)
    }
}