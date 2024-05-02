package com.example.hotelroom.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomFacility(
    @PrimaryKey(autoGenerate = true) val roomFacilityId: Int,
    val facilityName: String,
)