package com.example.diyhomeautomation.customs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.diyhomeautomation.R

class CustomHomeRoomsGridView(context: Context, resource: Int, objects: MutableList<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    private var mContext: Context
    private var mValues: MutableList<String>
    private var mResource: Int

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
        //vh.body?.text = value.body

        return view
    }


    private class MyViewHolder(view: View?) {
        val img = view?.findViewById<ImageView>(R.id.cardHomeRoom_img)
        val title = view?.findViewById<TextView>(R.id.cardHomeRoom_title_tv)
        val total = view?.findViewById<TextView>(R.id.cardHomeRoom_total_tv)
    }
}