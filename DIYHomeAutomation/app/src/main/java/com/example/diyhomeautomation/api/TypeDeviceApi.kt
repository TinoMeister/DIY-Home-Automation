package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.TaskDevice
import com.example.diyhomeautomation.models.TypeDevice
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Retrofit interface for handling TypeDevice API requests.
 */
interface TypeDeviceApi {
    /**
     * Retrieves all device types.
     *
     * @return A Retrofit Call containing a list of device types.
     */
    @GET("/typeDevices")
    fun getAllTypeDevices(): Call<List<TypeDevice>>

    /**
     * Posts a new device type.
     *
     * @param value TypeDevice to be posted.
     * @return A Retrofit Call containing the posted device type.
     */
    @POST("/typeDevices")
    fun postTypeDevice(@Body value: TypeDevice): Call<TypeDevice>

    /**
     * Updates an existing device type.
     *
     * @param id TypeDevice ID.
     * @param value TypeDevice with updated information.
     * @return A Retrofit Call containing the updated device type.
     */
    @PUT("/typeDevices/{id}")
    fun putTypeDevice(@Path("id") id: Int,
                      @Body value: TaskDevice): Call<TypeDevice>

    /**
     * Deletes a device type.
     *
     * @param id TypeDevice ID.
     * @return A Retrofit Call with no specific type for the deletion result.
     */
    @DELETE("/typeDevices/{id}")
    fun deleteTypeDevice(@Path("id") id: Int): Call<Any>
}
