package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.TaskDevice
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskDeviceApi {
    @GET("/tasksDevices")
    fun getAllTasksDevices(): Call<List<TaskDevice>>

    @POST("/tasksDevices")
    fun postTaskDevice(@Body value: TaskDevice): Call<TaskDevice>

    @PUT("/tasksDevices/{id}")
    fun putTaskDevice(@Path("id") id: Int, @Body value: TaskDevice): Call<TaskDevice>

    @DELETE("/tasksDevices/{id}")
    fun deleteTaskDevice(@Path("id") id: Int): Call<Any>
}