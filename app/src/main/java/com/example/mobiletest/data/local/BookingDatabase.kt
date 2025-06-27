package com.example.mobiletest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mobiletest.data.BookingEntity

@Database(entities = [BookingEntity::class], version = 1)
abstract class BookingDatabase : RoomDatabase() {
    abstract fun bookingDao(): BookingDao

    companion object {
        // 单例模式
        @Volatile
        private var instance: BookingDatabase? = null

        fun getInstance(context: Context): BookingDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context.applicationContext, BookingDatabase::class.java, "booking_database").build().also { instance = it }
            }
        }
    }
}