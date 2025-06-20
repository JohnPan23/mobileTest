package com.example.mobiletest.data.remote

import com.example.mobiletest.data.BookingResponse
import com.example.mobiletest.data.Location
import com.example.mobiletest.data.OriginAndDestinationPair
import com.example.mobiletest.data.Segment
import retrofit2.http.GET
import javax.inject.Inject

interface BookingService {
    @GET("booking")
    suspend fun getBookingData(): BookingResponse
}

// 实现类 - 模拟网络请求
class BookingServiceImpl @Inject constructor() : BookingService {
    override suspend fun getBookingData(): BookingResponse {

        // 返回模拟数据
        return BookingResponse(
            shipReference = "ABCDEF",
            shipToken = "AAAABBBCCCCDDD",
            canIssueTicketChecking = false,
            expiryTime = "1722409261",
            duration = 2430,
            segments = listOf(
                Segment(
                    id = 1,
                    originAndDestinationPair = OriginAndDestinationPair(
                        destination = Location("BBB", "BBB DisplayName", "www.ship.com"),
                        destinationCity = "AAA",
                        origin = Location("AAA", "AAA DisplayName", "www.ship.com"),
                        originCity = "BBB"
                    )
                ),
                Segment(
                    id = 2,
                    originAndDestinationPair = OriginAndDestinationPair(
                        destination = Location("CCC", "CCC DisplayName", "www.ship.com"),
                        destinationCity = "CCC",
                        origin = Location("BBB", "BBB DisplayName", "www.ship.com"),
                        originCity = "BBB"
                    )
                )
            )
        )
    }
}