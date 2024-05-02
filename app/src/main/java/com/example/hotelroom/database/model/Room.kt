package com.example.hotelroom.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = RoomType::class, parentColumns = ["roomTypeId"], childColumns = ["roomTypeId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Room(
    @PrimaryKey(autoGenerate = true) val roomId: Int,
    val roomNumber: Int,
    val roomTypeId: Int
)