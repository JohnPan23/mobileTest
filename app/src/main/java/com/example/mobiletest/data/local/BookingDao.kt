package com.example.mobiletest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobiletest.data.BookingEntity

@Dao
interface BookingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookingEntity: BookingEntity)

    @Query("SELECT * FROM booking_data WHERE id = 1")
    suspend fun getBookingData(): BookingEntity?

    @Query("DELETE FROM booking_data")
    suspend fun clearAll()
}