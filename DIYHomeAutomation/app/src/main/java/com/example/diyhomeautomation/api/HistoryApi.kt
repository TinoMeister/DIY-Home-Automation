package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.History
import retrofit2.Call
import retrofit2.http.GET

interface HistoryApi {
    @GET("/history")
    fun getAllHistories(): Call<List<History>>
}