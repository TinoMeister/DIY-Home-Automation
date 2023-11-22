package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.History
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HistoryApi {
    @GET("/histories")
    fun getAllHistories(): Call<List<History>>

    @GET("/histories/{deviceId}")
    fun getHistory(@Path("deviceId") deviceId: Int): Call<History>

    @POST("/histories")
    fun postHistory(@Body value: History): Call<History>


}