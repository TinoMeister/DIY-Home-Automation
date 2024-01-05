package com.example.diyhomeautomation.api

import retrofit2.Call
import com.example.diyhomeautomation.models.Room
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RoomApi {
    @GET("Room")
    fun getAllRooms(@Header("Authorization") token: String,
                    @Query("userId") userId: String): Call<List<Room>>
    @POST("Room")
    fun postRoom(@Header("Authorization") token: String,
                 @Body value: Room): Call<Room>

    @PUT("Room/{id}")
    fun putRoom(@Path("id") id: Int, @Body value: Room): Call<Room>

    @DELETE("Room/{id}")
    fun deleteRoom(@Path("id") id: Int): Call<Any>
}