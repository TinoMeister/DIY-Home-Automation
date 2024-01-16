package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.Task
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Retrofit interface for handling Task API requests.
 */
interface TaskApi {
    /**
     * Retrieves all tasks.
     *
     * @return A Retrofit Call containing a list of tasks.
     */
    @GET("/tasks")
    fun getAllTasks(): Call<List<Task>>

    /**
     * Posts a new task.
     *
     * @param value Task to be posted.
     * @return A Retrofit Call containing the posted task.
     */
    @POST("/tasks")
    fun postTask(@Body value: Task): Call<Task>

    /**
     * Updates an existing task.
     *
     * @param id Task ID.
     * @param value Task with updated information.
     * @return A Retrofit Call containing the updated task.
     */
    @PUT("/tasks/{id}")
    fun putTask(@Path("id") id: Int,
                @Body value: Task): Call<Task>

    /**
     * Deletes a task.
     *
     * @param id Task ID.
     * @return A Retrofit Call with no specific type for the deletion result.
     */
    @DELETE("/tasks/{id}")
    fun deleteTask(@Path("id") id: Int): Call<Any>
}