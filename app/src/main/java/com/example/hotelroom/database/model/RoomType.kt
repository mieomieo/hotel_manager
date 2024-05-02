package com.example.hotelroom.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomType(
    @PrimaryKey(autoGenerate = true) val roomTypeId: Int,
    val roomTypeName: String,
    val roomPrice: Double,
)