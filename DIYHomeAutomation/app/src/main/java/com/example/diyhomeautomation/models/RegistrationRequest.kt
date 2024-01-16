package com.example.diyhomeautomation.models

/**
 * Data class representing registration requests.
 *
 * @property name The user's name.
 * @property email The user's email address.
 * @property userName The user's chosen username.
 * @property password The user's password.
 *
 * @constructor Creates an instance of RegistrationRequest with the provided properties.
 */
data class RegistrationRequest (
    var name: String,
    var email: String,
    var userName: String,
    var password: String
)