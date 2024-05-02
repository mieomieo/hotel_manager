package com.example.hotelroom.database.model

import androidx.room.Embedded

data class ReservationWithClientRoomAndRoomType(
    @Embedded(prefix = "reservation_")
    val reservation: Reservation,
    @Embedded(prefix = "client_")
    val client: Client,
    @Embedded(prefix = "room_")
    val room: Room,
    @Embedded(prefix = "roomType_")
    val roomType: RoomType
)
