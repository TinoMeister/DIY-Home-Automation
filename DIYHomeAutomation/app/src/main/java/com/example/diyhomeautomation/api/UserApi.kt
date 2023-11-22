package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.User
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE

interface UserApi {
    @GET("/users")
    fun getAllUsers(): Call<List<User>>

    @GET("/users/")
    fun getUser(@Query("email") email: String,
                    @Query("password") password: String): Call<User>

    @POST("/users")
    fun postUser(@Body value: User): Call<User>

    @PUT("/users/{id}")
    fun putUser(@Path("id") id: Int, @Body value: User): Call<User>

    @DELETE("/users/{id}")
    fun deleteUser(@Path("id") id: Int): Call<Any>
}