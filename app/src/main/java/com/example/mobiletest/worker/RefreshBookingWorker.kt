package com.example.mobiletest.worker

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.mobiletest.data.repository.BookingRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RefreshBookingWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @Inject
    lateinit var bookingRepository: BookingRepository

    override suspend fun doWork(): Result {
        return try {
            // 刷新数据
            bookingRepository.refreshBookingData()
            Result.success()
        } catch (e: Exception) {
            Result.retry() // 失败后重试
        }
    }

    companion object {
        const val WORK_TAG = "refresh_booking_work"

        fun enqueueRefresh(context: Context) {
            val workRequest = OneTimeWorkRequestBuilder<RefreshBookingWorker>()
                .addTag(WORK_TAG)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()

            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}