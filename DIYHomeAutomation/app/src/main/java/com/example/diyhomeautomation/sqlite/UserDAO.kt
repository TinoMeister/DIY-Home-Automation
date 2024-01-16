package com.example.diyhomeautomation.sqlite

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.diyhomeautomation.models.AuthRequest

/**
 * Class implementing [IUser] interface for managing user authentication data in a SQLite database.
 *
 * @property write Writable instance of the SQLite database.
 */
class UserDAO(context: Context) : IUser {
    private val write = DatabaseHelper(context).writableDatabase
    //private val read = DatabaseHelper(context).readableDatabase

    /**
     * Inserts user authentication data into the database.
     *
     * @param user The [AuthRequest] object containing user authentication information.
     * @return `true` if the insertion is successful, `false` otherwise.
     */
    override fun insertUser(user: AuthRequest): Boolean {
        val contents = ContentValues()
        contents.put(DatabaseHelper.AUTHREQUEST_EMAIL, user.email)
        contents.put(DatabaseHelper.AUTHREQUEST_PASSWORD, user.password)

        try {
            write.insert(
                DatabaseHelper.TABLENAME_AUTHREQUEST,
                null,
                contents
            )
            Log.i("UserDAO", "Success inserting in database")
            return true
        } catch (e: Exception) {
            Log.e("UserDAO", "Error adding in database")
            return false
        }
    }
}