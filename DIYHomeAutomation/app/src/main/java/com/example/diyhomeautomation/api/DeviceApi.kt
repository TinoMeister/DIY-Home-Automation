package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.Device
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DeviceApi {
    @GET("/devices")
    fun getAllDevices(): Call<List<Device>>

    @GET("/devices/{roomId}")
    fun getDevice(@Path("roomId") roomId: Int): Call<Device>

    @POST("/devices")
    fun postDevice(@Body value: Device): Call<Device>

    @PUT("/devices/{id}")
    fun putDevice(@Path("id") id: Int, @Body value: Device): Call<Device>

    @DELETE("/devices/{id}")
    fun deleteDevice(@Path("id") id: Int): Call<Any>
}