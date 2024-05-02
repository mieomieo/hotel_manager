package com.example.hotelroom.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = RoomType::class,
        parentColumns = ["roomTypeId"],
        childColumns = ["roomTypeId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RoomPhoto(
    @PrimaryKey(autoGenerate = true) val photoId: Int,
    val roomTypeId: Int,
    val photoData: String,
    val isDefault: Boolean
)