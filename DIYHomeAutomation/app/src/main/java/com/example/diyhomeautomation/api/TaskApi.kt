package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.Task
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApi {
    @GET("/tasks")
    fun getAllTasks(): Call<List<Task>>

    @POST("/tasks")
    fun postTask(@Body value: Task): Call<Task>

    @PUT("/tasks/{id}")
    fun putTask(@Path("id") id: Int, @Body value: Task): Call<Task>

    @DELETE("/tasks/{id}")
    fun deleteTask(@Path("id") id: Int): Call<Any>
}