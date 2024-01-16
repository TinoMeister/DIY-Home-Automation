package com.example.diyhomeautomation.models

/**
 * Data class representing an authentication request.
 *
 * @property email The email address associated with the authentication request.
 * @property password The password associated with the authentication request.
 *
 * @constructor Creates an instance of AuthRequest with the provided email and password.
 */
data class AuthRequest (
    var email: String,
    var password: String
)