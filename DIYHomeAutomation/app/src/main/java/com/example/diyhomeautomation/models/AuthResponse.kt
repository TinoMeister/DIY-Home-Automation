package com.example.diyhomeautomation.models

/**
 * Data class representing an authentication response.
 *
 * @property userID The user ID associated with the authentication response.
 * @property token The authentication token associated with the response.
 * @property expiration The expiration date/time of the authentication token.
 *
 * @constructor Creates an instance of AuthResponse with the provided user ID, token, and expiration.
 */
data class AuthResponse (
    var userID: String,
    var token: String,
    var expiration: String
)