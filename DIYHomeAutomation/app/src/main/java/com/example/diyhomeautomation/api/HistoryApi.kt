package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.History
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Retrofit interface for handling History API requests.
 */
interface HistoryApi {
    /**
     * Retrieves all historical data entries.
     *
     * @return A Retrofit Call containing a list of historical data entries.
     */
    @GET("/histories")
    fun getAllHistories(): Call<List<History>>

    /**
     * Retrieves historical data entries associated with a specific device.
     *
     * @param deviceId Device ID.
     * @return A Retrofit Call containing the historical data entry.
     */
    @GET("/histories/{deviceId}")
    fun getHistory(@Path("deviceId") deviceId: Int): Call<History>

    /**
     * Posts a new historical data entry.
     *
     * @param value Historical data entry to be posted.
     * @return A Retrofit Call containing the posted historical data entry.
     */
    @POST("/histories")
    fun postHistory(@Body value: History): Call<History>
}
