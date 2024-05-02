package com.example.hotelroom.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.hotelroom.database.model.Room
import com.example.hotelroom.database.model.RoomWithRoomType
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Insert
    suspend fun insertRoom(room: Room)

    @Transaction
    @Query("SELECT Room.roomId as room_roomId, Room.roomNumber as room_roomNumber, Room.roomTypeId as room_roomTypeId, RoomType.roomTypeId as roomType_roomTypeId, RoomType.roomTypeName as roomType_roomTypeName, RoomType.roomPrice as roomType_roomPrice FROM Room INNER JOIN RoomType ON Room.roomTypeId = RoomType.roomTypeId")
    fun getRoomsWithRoomType(): Flow<List<RoomWithRoomType>>

    @Query("DELETE FROM Room WHERE roomId = :roomId")
    suspend fun deleteRoom(roomId: Int)
}