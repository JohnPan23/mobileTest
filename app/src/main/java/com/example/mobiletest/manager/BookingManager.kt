package com.example.mobiletest.manager

import android.content.Context
import com.example.mobiletest.data.BookingResponse
import com.example.mobiletest.data.repository.BookingRepository
import com.example.mobiletest.worker.RefreshBookingWorker
import javax.inject.Inject

class BookingManager @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val context: Context
) {
    suspend fun getBookingData(): BookingResponse {
        return try {
            val data = bookingRepository.getBookingData()
            // 检查数据是否过期
            val localData = bookingRepository.getLocalBookingData()
            val isDataExpired = localData?.let {
                System.currentTimeMillis() - it.lastUpdated > bookingRepository.DATA_EXPIRY_TIME
            } ?: true

            if (isDataExpired) {
                // 数据已过期，触发后台刷新
                RefreshBookingWorker.enqueueRefresh(context)
            }
            data
        } catch (e: Exception) {
            // 错误处理
            throw BookingException("Failed to get booking data", e)
        }
    }
}

class BookingException(message: String, cause: Throwable) : Exception(message, cause)