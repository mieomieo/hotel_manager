package com.example.hotelroom.database.model

import androidx.room.Embedded

data class RoomWithRoomType(
    @Embedded(prefix = "room_")
    val room: Room,
    @Embedded(prefix = "roomType_")
    val roomType: RoomType
)