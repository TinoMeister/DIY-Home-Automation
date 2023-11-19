package com.example.diyhomeautomation.homes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.diyhomeautomation.R
import com.google.android.material.textfield.TextInputLayout

class DeviceAddActivity : AppCompatActivity() {
    private val dropdown : TextInputLayout by lazy {
        findViewById(R.id.deviceAdd_ddlst)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_add)

        val items = listOf("AA","BB","CC","DD","EE","FF","GG","HH","II","JJ","KK","LL",
            "MM","NN","OO","PP","QQ","RR","SS","TT","UU","VV")
        val adapter = ArrayAdapter(baseContext, R.layout.custom_device_dropdownlist, items)
        (dropdown.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
}