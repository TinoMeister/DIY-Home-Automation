package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.Restriction
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RestrictionApi {
    @GET("/restrictions")
    fun getAllRestrictions(): Call<List<Restriction>>

    @POST("/restrictions")
    fun postRestriction(@Body value: Restriction): Call<Restriction>

    @PUT("/restrictions/{id}")
    fun putRestriction(@Path("id") id: Int, @Body value: Restriction): Call<Restriction>

    @DELETE("/restrictions/{id}")
    fun deleteRestriction(@Path("id") id: Int): Call<Any>
}