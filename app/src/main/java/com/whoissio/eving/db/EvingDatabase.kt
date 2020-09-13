package com.whoissio.eving.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.whoissio.eving.models.BleDevice

@Database(entities = arrayOf(BleDevice::class), version = 1, exportSchema = false)
abstract class EvingDatabase : RoomDatabase() {

    abstract fun bleDao(): BleDao

    companion object {
        @Volatile
        private var INSTANCE: EvingDatabase? = null

        fun getDatabse(context: Context): EvingDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EvingDatabase::class.java,
                    "eving_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}