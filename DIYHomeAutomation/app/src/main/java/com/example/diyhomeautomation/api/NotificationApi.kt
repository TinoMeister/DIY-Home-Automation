package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.Notification
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Retrofit interface for handling Notification API requests.
 */
interface NotificationApi {
    /**
     * Retrieves all notifications.
     *
     * @return A Retrofit Call containing a list of notifications.
     */
    @GET("/notifications")
    fun getAllNotifications(): Call<List<Notification>>

    /**
     * Posts a new notification.
     *
     * @param value Notification to be posted.
     * @return A Retrofit Call containing the posted notification.
     */
    @POST("/notifications")
    fun postNotification(@Body value: Notification): Call<Notification>

    /**
     * Updates an existing notification.
     *
     * @param id Notification ID.
     * @param value Notification with updated information.
     * @return A Retrofit Call containing the updated notification.
     */
    @PUT("/notifications/{id}")
    fun putNotification(@Path("id") id: Int,
                        @Body value: Notification): Call<Notification>

    /**
     * Deletes a notification.
     *
     * @param id Notification ID.
     * @return A Retrofit Call with no specific type for the deletion result.
     */
    @DELETE("/notifications/{id}")
    fun deleteNotification(@Path("id") id: Int): Call<Any>
}