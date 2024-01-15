package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.Device
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Retrofit interface for handling Device API requests.
 */
interface DeviceApi {
    /**
     * Retrieves all devices associated with a specific user.
     *
     * @param token Authorization token.
     * @param userId User ID.
     * @return A Retrofit Call containing a list of devices.
     */
    @GET("Device/{userId}")
    fun getAllDevices(@Header("Authorization") token: String,
                      @Path("userId") userId: String): Call<List<Device>>

    /**
     * Retrieves devices associated with a specific room.
     *
     * @param token Authorization token.
     * @param roomId Room ID.
     * @return A Retrofit Call containing a list of devices.
     */
    @GET("Device/Room/{roomId}")
    fun getDevice(@Header("Authorization") token: String,
                  @Path("roomId") roomId: Int): Call<List<Device>>

    /**
     * Posts a new device.
     *
     * @param token Authorization token.
     * @param value Device object to be posted.
     * @return A Retrofit Call containing the posted device.
     */
    @POST("Device")
    fun postDevice(@Header("Authorization") token: String,
                   @Body value: Device): Call<Device>

    /**
     * Updates an existing device.
     *
     * @param token Authorization token.
     * @param id Device ID.
     * @param value Updated device object.
     * @return A Retrofit Call with no content.
     */
    @PUT("Device/{id}")
    fun putDevice(@Header("Authorization") token: String,
                  @Path("id") id: Int, @Body value: Device): Call<Void>

    /**
     * Deletes a device.
     *
     * @param token Authorization token.
     * @param id Device ID.
     * @return A Retrofit Call with no content.
     */
    @DELETE("Device/{id}")
    fun deleteDevice(@Header("Authorization") token: String,
                     @Path("id") id: Int): Call<Void>
}
