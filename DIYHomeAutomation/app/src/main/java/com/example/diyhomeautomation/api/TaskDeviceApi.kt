package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.TaskDevice
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Retrofit interface for handling TaskDevice API requests.
 */
interface TaskDeviceApi {
    /**
     * Retrieves all task-device associations.
     *
     * @return A Retrofit Call containing a list of task-device associations.
     */
    @GET("/tasksDevices")
    fun getAllTasksDevices(): Call<List<TaskDevice>>

    /**
     * Posts a new task-device association.
     *
     * @param value TaskDevice to be posted.
     * @return A Retrofit Call containing the posted task-device association.
     */
    @POST("/tasksDevices")
    fun postTaskDevice(@Body value: TaskDevice): Call<TaskDevice>

    /**
     * Updates an existing task-device association.
     *
     * @param id TaskDevice ID.
     * @param value TaskDevice with updated information.
     * @return A Retrofit Call containing the updated task-device association.
     */
    @PUT("/tasksDevices/{id}")
    fun putTaskDevice(@Path("id") id: Int,
                      @Body value: TaskDevice): Call<TaskDevice>

    /**
     * Deletes a task-device association.
     *
     * @param id TaskDevice ID.
     * @return A Retrofit Call with no specific type for the deletion result.
     */
    @DELETE("/tasksDevices/{id}")
    fun deleteTaskDevice(@Path("id") id: Int): Call<Any>
}
