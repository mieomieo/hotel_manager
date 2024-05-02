package com.example.hotelroom.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
@Entity(
    primaryKeys = ["roomTypeId", "roomFacilityId"], foreignKeys = [ForeignKey(
        entity = RoomType::class, parentColumns = ["roomTypeId"], childColumns = ["roomTypeId"]
    ), ForeignKey(
        entity = RoomFacility::class,
        parentColumns = ["roomFacilityId"],
        childColumns = ["roomFacilityId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RoomTypeHasRoomFacility(
    @ColumnInfo(name = "roomTypeId") val roomTypeId: Int,
    @ColumnInfo(name = "roomFacilityId") val roomFacilityId: Int
)