package com.example.mobiletest.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// 分段航运
data class BookingResponse(
    // 船舶参考号
    @SerializedName("shipReference") val shipReference: String,
    // 船舶令牌
    @SerializedName("shipToken") val shipToken: String,
    // 能否签发票务检查
    @SerializedName("canIssueTicketChecking") val canIssueTicketChecking: Boolean,
    // 运输服务的时效性要求：有效期
    @SerializedName("expiryTime") val expiryTime: String,
    // 运输服务的时效性要求：时长
    @SerializedName("duration") val duration: Int,
    // 分段航运
    @SerializedName("segments") val segments: List<Segment>
)

data class Segment(
    @SerializedName("id") val id: Int,
    @SerializedName("originAndDestinationPair") val originAndDestinationPair: OriginAndDestinationPair
)

data class OriginAndDestinationPair(
    // 目的地、城市
    @SerializedName("destination") val destination: Location,
    @SerializedName("destinationCity") val destinationCity: String,
    // 起运点、城市
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