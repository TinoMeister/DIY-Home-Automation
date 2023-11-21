package com.example.diyhomeautomation.api

import android.devicelock.DeviceId
import com.example.diyhomeautomation.models.Device
import com.example.diyhomeautomation.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DeviceApi {
    @GET("/device")
    fun getAllDevices(): Call<List<Device>>

    @GET("/device/{roomId}")
    fun getDevice(@Path("roomId") roomId: Int): Call<Device>

    @POST("/device")
    fun postDevice(@Body value: Device): Call<Device>

    @PUT("/device/{id}")
    fun putDevice(@Path("id") id: Int, @Body value: Device): Call<Device>

    @DELETE("/device/{id}")
    fun deleteDevice(@Path("id") id: Int): Call<Any>
}