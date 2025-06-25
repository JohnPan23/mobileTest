package com.example.mobiletest.data.repository

import com.example.mobiletest.data.BookingEntity
import com.example.mobiletest.data.BookingResponse
import com.example.mobiletest.data.local.BookingDao
import com.example.mobiletest.data.remote.BookingService
import com.google.gson.Gson
import javax.inject.Inject

class BookingRepository @Inject constructor(
    private val bookingService: BookingService,
    private val bookingDao: BookingDao,
    private val gson: Gson
) {
    // 数据过期时间5分钟
    private val DATA_EXPIRY_TIME = 5 * 60 * 1000L

    // 添加获取本地数据的方法
    suspend fun getLocalBookingData(): BookingEntity? {
        return bookingDao.getBookingData()
    }

    suspend fun getBookingData(): BookingResponse {
        // 1. 先尝试从本地获取
        val localData = bookingDao.getBookingData()

        // 2. 检查数据是否过期
        val isDataExpired = localData?.let {
            System.currentTimeMillis() - it.lastUpdated > DATA_EXPIRY_TIME
        } ?: true

        // 3. 如果数据不存在或已过期，触发刷新
        if (localData == null || isDataExpired) {
            try {
                // 从网络获取新数据
                val newData = bookingService.getBookingData()
                // 保存到数据库
                bookingDao.insert(
                    BookingEntity(
                        data = gson.toJson(newData),
                        lastUpdated = System.currentTimeMillis()
                    )
                )
                return newData
            } catch (e: Exception) {
                // 网络请求失败，如果有本地数据则返回本地数据
                if (localData != null) {
                    return gson.fromJson(localData.data, BookingResponse::class.java)
                }
                throw e // 没有本地数据且网络请求失败，抛出异常
            }
        }

        // 4. 返回本地数据
        return gson.fromJson(localData.data, BookingResponse::class.java)
    }

    // 强制刷新数据
    suspend fun refreshBookingData(): BookingResponse {
        try {
            val newData = bookingService.getBookingData()
            bookingDao.insert(
                BookingEntity(
                    data = gson.toJson(newData),
                    lastUpdated = System.currentTimeMillis()
                )
            )
            return newData
        } catch (e: Exception) {
            throw e
        }
    }
}