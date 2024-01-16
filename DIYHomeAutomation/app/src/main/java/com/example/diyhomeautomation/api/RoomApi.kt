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

/**
 * Retrofit interface for handling Room API requests.
 */
interface RoomApi {
    /**
     * Retrieves all rooms for a specific user.
     *
     * @param token Authorization token.
     * @param userId User ID for whom rooms are retrieved.
     * @return A Retrofit Call containing a list of rooms.
     */
    @GET("Room/{userId}")
    fun getAllRooms(@Header("Authorization") token: String,
                    @Path("userId") userId: String): Call<List<Room>>

    /**
     * Posts a new room.
     *
     * @param token Authorization token.
     * @param value Room to be posted.
     * @return A Retrofit Call containing the posted room.
     */
    @POST("Room")
    fun postRoom(@Header("Authorization") token: String,
                 @Body value: Room): Call<Room>

    /**
     * Updates an existing room.
     *
     * @param id Room ID.
     * @param value Room with updated information.
     * @return A Retrofit Call containing the updated room.
     */
    @PUT("Room/{id}")
    fun putRoom(@Header("Authorization") token: String,
                @Path("id") id: Int,
                @Body value: Room): Call<Void>

    /**
     * Deletes a room.
     *
     * @param id Room ID.
     * @return A Retrofit Call with no specific type for the deletion result.
     */
    @DELETE("Room/{id}")
    fun deleteRoom(@Header("Authorization") token: String,
                   @Path("id") id: Int): Call<Void>
}