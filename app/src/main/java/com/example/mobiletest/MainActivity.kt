package com.example.mobiletest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mobiletest.manager.BookingException
import com.example.mobiletest.manager.BookingManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var bookingManager: BookingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 每次Activity显示时都获取数据
        loadBookingData()
    }

    override fun onResume() {
        super.onResume()
        // 确保每次显示时都获取最新数据
        loadBookingData()
    }

    private fun loadBookingData() {
        lifecycleScope.launch {
            try {
                val bookingData = bookingManager.getBookingData()
                // 在控制台打印数据
                Log.d("BookingDemo", "Booking Data: $bookingData")
            } catch (e: BookingException) {
                Log.e("BookingDemo", "Error getting booking data", e)
            }
        }
    }
}