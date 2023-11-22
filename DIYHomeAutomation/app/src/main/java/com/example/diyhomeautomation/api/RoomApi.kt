package com.example.diyhomeautomation.api

import retrofit2.Call
import com.example.diyhomeautomation.models.Room
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RoomApi {
    @GET("/rooms")
    fun getAllRooms(): Call<List<Room>>

    @POST("/rooms")
    fun postRoom(@Body value: Room): Call<Room>

    @PUT("/rooms/{id}")
    fun putRoom(@Path("id") id: Int, @Body value: Room): Call<Room>

    @DELETE("/rooms/{id}")
    fun deleteRoom(@Path("id") id: Int): Call<Any>
}