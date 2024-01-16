package com.example.diyhomeautomation.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Helper class for managing the SQLite database in DIY home automation.
 *
 * @param context The application context.
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, VERSION
) {
    companion object {
        const val DATABASE_NAME = "sensorDb"
        const val VERSION = 20

        const val TABLENAME_AUTHREQUEST = "auth_request"
        const val AUTHREQUEST_EMAIL = "auth_email"
        const val AUTHREQUEST_PASSWORD = "auth_password"

        const val TABLENAME_ROOM = "room"
        const val ROOM_ID = "room_id"
        const val ROOM_NAME = "room_name"
        const val ROOM_ICON = "room_icon"
        const val ROOM_USERID = "room_userid"
        const val ROOM_ESPID = "room_espid"

        const val TABLENAME_DEVICE = "device"
        const val DEVICE_ID = "device_id"
        const val DEVICE_NAME = "device_name"
        const val DEVICE_PINVALUE = "device_pinvalue"
        const val DEVICE_STATE = "device_state"
        const val DEVICE_VALUE = "device_value"
        const val DEVICE_ICON = "device_icon"
        const val DEVICE_ROOMID = "device_roomid"
        const val DEVICE_TYPEDEVICEID = "device_typedeviceid"
        const val DEVICE_TASKDEVICES = "device_list_taskdevices"

        const val TABLENAME_TYPEDEVICE = "typedevice"
        const val TYPEDEVICE_ID = "typedevice_id"
        const val TYPEDEVICE_NAME = "typedevice_name"

        const val TABLENAME_TASKDEVICE = "taskdevice"
        const val TASKDEVICE_ID = "taskdevice_id"
        const val TASKDEVICE_TASKID = "taskdevice_taskid"
        const val TASKDEVICE_DEVICEID = "taskdevice_deviceid"

        const val TABLENAME_TASK = "task"
        const val TASK_ID = "task_id"
        const val TASK_NAME = "task_name"
        const val TASK_STATE = "task_state"
        const val TASK_INIT_HOUR = "task_init_hour"
        const val TASK_END_HOUR = "task_end_hour"
        const val TASK_DAYS_OF_WEEK = "task_days_of_week"
        const val TASK_TASKDEVICES = "task_list_taskdevices"
    }

    /**
     * Called when the database is created for the first time. This method creates the required tables
     * for the DIY home automation app.
     *
     * @param db The database.
     */
    override fun onCreate(db: SQLiteDatabase?) {

        // SQL statement to create the AuthRequest table
        val authTableSql = """
            CREATE TABLE $TABLENAME_AUTHREQUEST (
                $AUTHREQUEST_EMAIL TEXT PRIMARY KEY,
                $AUTHREQUEST_PASSWORD TEXT
            );
        """.trimIndent()

        // SQL statement to create the Room table
        val roomTableSql = """
            CREATE TABLE $TABLENAME_ROOM (
                $ROOM_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
                $ROOM_NAME TEXT NOT NULL,
                $ROOM_ICON TEXT, 
                $ROOM_USERID TEXT NOT NULL, 
                $ROOM_ESPID INTEGER NOT NULL
            );
        """.trimIndent()

        // SQL statement to create the Device table
        val deviceTableSql = """
            CREATE TABLE $TABLENAME_DEVICE (
                $DEVICE_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
                $DEVICE_NAME TEXT NOT NULL, 
                $DEVICE_PINVALUE TEXT NOT NULL, 
                $DEVICE_STATE INTEGER NOT NULL, 
                $DEVICE_VALUE REAL, 
                $DEVICE_ICON TEXT, 
                $DEVICE_ROOMID INTEGER NOT NULL, 
                $DEVICE_TYPEDEVICEID INTEGER, 
                $DEVICE_TASKDEVICES TEXT, 
                FOREIGN KEY($DEVICE_ROOMID) REFERENCES $TABLENAME_ROOM($ROOM_ID),
                FOREIGN KEY($DEVICE_TYPEDEVICEID) REFERENCES $TABLENAME_TYPEDEVICE($TYPEDEVICE_ID)
            );
        """.trimIndent()

        // SQL statement to create the TypeDevice table
        val typeDeviceTableSql = """
            CREATE TABLE $TABLENAME_TYPEDEVICE (
                $TYPEDEVICE_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
                $TYPEDEVICE_NAME TEXT NOT NULL
            );
        """.trimIndent()

        // SQL statement to create the TaskDevice table
        val taskDeviceTableSql = """
            CREATE TABLE $TABLENAME_TASKDEVICE (
                $TASKDEVICE_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
                $TASKDEVICE_TASKID INTEGER NOT NULL, 
                $TASKDEVICE_DEVICEID INTEGER NOT NULL,
                FOREIGN KEY($TASKDEVICE_TASKID) REFERENCES $TABLENAME_TASK($TASK_ID),
                FOREIGN KEY($TASKDEVICE_DEVICEID) REFERENCES $TABLENAME_DEVICE($DEVICE_ID)
            );
        """.trimIndent()

        // SQL statement to create the Task table
        val taskTableSql = """
            CREATE TABLE $TABLENAME_TASK (
                $TASK_ID INTEGER PRIMARY KEY, 
                $TASK_NAME TEXT NOT NULL, 
                $TASK_STATE INTEGER NOT NULL, 
                $TASK_INIT_HOUR TEXT NOT NULL,  
                $TASK_END_HOUR TEXT NOT NULL,   
                $TASK_DAYS_OF_WEEK TEXT NOT NULL,
                $TASK_TASKDEVICES TEXT,      
                FOREIGN KEY($TASK_ID) REFERENCES $TABLENAME_TASKDEVICE($TASKDEVICE_TASKID)
            );
        """.trimIndent()

        try {
            // Attempt to create the AuthRequest table
            db?.execSQL(authTableSql)
            Log.e("DatabaseHelper", "AuthRequest table created successfully!")
        } catch (e: Exception) {
            // Log and print the exception if an error occurs during AuthRequest table creation
            e.printStackTrace()
            Log.e("DatabaseHelper", "Error creating AuthRequest table!")
        }

        try {
            // Attempt to create the Room table
            db?.execSQL(roomTableSql)
            Log.e("DatabaseHelper", "Room table created successfully!")
        } catch (e: Exception) {
            // Log and print the exception if an error occurs during Room table creation
            e.printStackTrace()
            Log.e("DatabaseHelper", "Error creating Room table!")
        }

        try {
            // Attempt to create the Device table
            db?.execSQL(deviceTableSql)
            Log.e("DatabaseHelper", "Device table created successfully!")
        } catch (e: Exception) {
            // Log and print the exception if an error occurs during Device table creation
            e.printStackTrace()
            Log.e("DatabaseHelper", "Error creating Device table!")
        }

        try {
            // Attempt to create the TypeDevice table
            db?.execSQL(typeDeviceTableSql)
            Log.e("DatabaseHelper", "TypeDevice table created successfully!")
        } catch (e: Exception) {
            // Log and print the exception if an error occurs during TypeDevice table creation
            e.printStackTrace()
            Log.e("DatabaseHelper", "Error creating TypeDevice table!")
        }

        try {
            // Attempt to create the TaskDevice table
            db?.execSQL(taskDeviceTableSql)
            Log.e("DatabaseHelper", "TaskDevice table created successfully!")
        } catch (e: Exception) {
            // Log and print the exception if an error occurs during TaskDevice table creation
            e.printStackTrace()
            Log.e("DatabaseHelper", "Error creating TaskDevice table!")
        }

        try {
            // Attempt to create the Task table
            db?.execSQL(taskTableSql)
            Log.e("DatabaseHelper", "Task table created successfully!")
        } catch (e: Exception) {
            // Log and print the exception if an error occurs during Task table creation
            e.printStackTrace()
            Log.e("DatabaseHelper", "Error creating Task table!")
        }
    }

    /**
     * Called when the database needs to be upgraded. This method will drop the existing tables
     * and recreate them to handle changes in the database schema.
     *
     * @param db The database.
     * @param oldVersion The old version of the database.
     * @param newVersion The new version of the database.
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            // Handle the upgrade by dropping existing tables and recreating them
            db?.execSQL("DROP TABLE IF EXISTS $TABLENAME_AUTHREQUEST")
            db?.execSQL("DROP TABLE IF EXISTS $TABLENAME_ROOM")
            db?.execSQL("DROP TABLE IF EXISTS $TABLENAME_DEVICE")
            db?.execSQL("DROP TABLE IF EXISTS $TABLENAME_TYPEDEVICE")
            db?.execSQL("DROP TABLE IF EXISTS $TABLENAME_TASKDEVICE")
            db?.execSQL("DROP TABLE IF EXISTS $TABLENAME_TASK")

            // Recreate the tables
            onCreate(db)
        }
    }
}