package com.example.mobiletest.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class BookingResponse(
    @SerializedName("shipReference") val shipReference: String,
    @SerializedName("shipToken") val shipToken: String,
    @SerializedName("canIssueTicketChecking") val canIssueTicketChecking: Boolean,
    @SerializedName("expiryTime") val expiryTime: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("segments") val segments: List<Segment>
)

data class Segment(
    @SerializedName("id") val id: Int,
    @SerializedName("originAndDestinationPair") val originAndDestinationPair: OriginAndDestinationPair
)

data class OriginAndDestinationPair(
    @SerializedName("destination") val destination: Location,
    @SerializedName("destinationCity") val destinationCity: String,
    @SerializedName("origin") val origin: Location,
    @SerializedName("originCity") val originCity: String
)

data class Location(
    @SerializedName("code") val code: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("url") val url: String
)

// 用于Room数据库的实体类
@Entity(tableName = "booking_data")
data class BookingEntity(
    @PrimaryKey val id: Int = 1, // 只保存一条数据
    val data: String, // 存储JSON字符串
    val lastUpdated: Long // 最后更新时间
)