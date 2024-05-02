package com.example.hotelroom.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Client::class,
        parentColumns = ["clientId"],
        childColumns = ["clientId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Room::class,
        parentColumns = ["roomId"],
        childColumns = ["roomId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Reservation(
    @PrimaryKey(autoGenerate = true)
    val reservationId: Int,
    val clientId: Int,
    val checkInDate: Long,
    val checkOutDate: Long,
    val additionalExpenses: Double
)