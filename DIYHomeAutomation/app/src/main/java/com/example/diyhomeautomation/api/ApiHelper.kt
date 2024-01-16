package com.example.diyhomeautomation.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Helper class for configuring and obtaining an instance of Retrofit for API communication.
 */
class ApiHelper {
    // Base URL for the API
    private val baseUrl = "http://192.168.1.91:5212/api/" //"http://129.168.1.21/api/", http://192.168.1.137:5212/api/

    /**
     * Provides an instance of Retrofit configured with the base URL and Gson converter factory.
     *
     * @return An instance of Retrofit.
     */
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
