package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.TaskDevice
import com.example.diyhomeautomation.models.TypeDevice
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TypeDeviceApi {
    @GET("/typeDevices")
    fun getAllTypeDevices(): Call<List<TypeDevice>>

    @POST("/typeDevices")
    fun postTypeDevice(@Body value: TypeDevice): Call<TypeDevice>

    @PUT("/typeDevices/{id}")
    fun putTypeDevice(@Path("id") id: Int, @Body value: TaskDevice): Call<TypeDevice>

    @DELETE("/typeDevices/{id}")
    fun deleteTypeDevice(@Path("id") id: Int): Call<Any>
}