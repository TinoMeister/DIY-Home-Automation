package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.Notification
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotificationApi {
    @GET("/notifications")
    fun getAllNotifications(): Call<List<Notification>>

    @POST("/notifications")
    fun postNotification(@Body value: Notification): Call<Notification>

    @PUT("/notifications/{id}")
    fun putNotification(@Path("id") id: Int, @Body value: Notification): Call<Notification>

    @DELETE("/notifications/{id}")
    fun deleteNotification(@Path("id") id: Int): Call<Any>

}