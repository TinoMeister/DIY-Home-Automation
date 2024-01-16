package com.example.diyhomeautomation.api

import com.example.diyhomeautomation.models.Restriction
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Retrofit interface for handling Restriction API requests.
 */
interface RestrictionApi {
    /**
     * Retrieves all restrictions.
     *
     * @return A Retrofit Call containing a list of restrictions.
     */
    @GET("/restrictions")
    fun getAllRestrictions(): Call<List<Restriction>>

    /**
     * Posts a new restriction.
     *
     * @param value Restriction to be posted.
     * @return A Retrofit Call containing the posted restriction.
     */
    @POST("/restrictions")
    fun postRestriction(@Body value: Restriction): Call<Restriction>

    /**
     * Updates an existing restriction.
     *
     * @param id Restriction ID.
     * @param value Restriction with updated information.
     * @return A Retrofit Call containing the updated restriction.
     */
    @PUT("/restrictions/{id}")
    fun putRestriction(@Path("id") id: Int,
                       @Body value: Restriction): Call<Restriction>

    /**
     * Deletes a restriction.
     *
     * @param id Restriction ID.
     * @return A Retrofit Call with no specific type for the deletion result.
     */
    @DELETE("/restrictions/{id}")
    fun deleteRestriction(@Path("id") id: Int): Call<Any>
}
