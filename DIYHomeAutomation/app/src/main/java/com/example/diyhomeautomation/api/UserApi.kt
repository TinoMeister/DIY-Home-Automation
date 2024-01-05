package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.AuthRequest
import com.example.diyhomeautomation.models.AuthResponse
import com.example.diyhomeautomation.models.RegistrationRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("Users/Login")
    fun getUser(@Body value: AuthRequest): Call<AuthResponse>

    @POST("Users/Register")
    fun postUser(@Body value: RegistrationRequest): Call<AuthResponse>
}