package com.example.mobiletest.di

import android.content.Context
import com.example.mobiletest.data.local.BookingDao
import com.example.mobiletest.data.local.BookingDatabase
import com.example.mobiletest.data.remote.BookingService
import com.example.mobiletest.data.remote.BookingServiceImpl
import com.example.mobiletest.data.repository.BookingRepository
import com.example.mobiletest.manager.BookingManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {  // 改为 object 而不是 class
    // 所有提供方法也需要是静态的
    @Provides
    @Singleton
    fun provideBookingDatabase(@ApplicationContext context: Context): BookingDatabase {
        return BookingDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideBookingDao(database: BookingDatabase): BookingDao {
        return database.bookingDao()
    }

    @Provides
    @Singleton
    fun provideBookingService(): BookingService {
        return BookingServiceImpl()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideBookingRepository(
        service: BookingService,
        dao: BookingDao,
        gson: Gson
    ): BookingRepository {
        return BookingRepository(service, dao, gson)
    }

    @Provides
    @Singleton
    fun provideBookingManager(repository: BookingRepository, @ApplicationContext context: Context): BookingManager {
        return BookingManager(repository, context)
    }
}