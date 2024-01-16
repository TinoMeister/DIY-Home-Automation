package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.AuthRequest
import com.example.diyhomeautomation.models.AuthResponse
import com.example.diyhomeautomation.models.RegistrationRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit interface for handling user-related API requests.
 */
interface UserApi {
    /**
     * Logs in a user.
     *
     * @param value AuthRequest containing user login information.
     * @return A Retrofit Call containing the authentication response.
     */
    @POST("Users/Login")
    fun getUser(@Body value: AuthRequest): Call<AuthResponse>

    /**
     * Registers a new user.
     *
     * @param value RegistrationRequest containing user registration information.
     * @return A Retrofit Call containing the authentication response.
     */
    @POST("Users/Register")
    fun postUser(@Body value: RegistrationRequest): Call<AuthResponse>
}
