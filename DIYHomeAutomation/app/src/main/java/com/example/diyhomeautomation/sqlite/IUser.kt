package com.example.diyhomeautomation.sqlite

import com.example.diyhomeautomation.models.AuthRequest

/**
 * Interface defining operations for managing user authentication requests in a database.
 */
interface IUser {
    /**
     * Inserts a user authentication request into the database.
     *
     * @param user The authentication request object to be inserted.
     * @return `true` if the insertion is successful, `false` otherwise.
     */
    fun insertUser(user: AuthRequest): Boolean
}
