package com.example.hotelroom.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hotelroom.database.model.RoomType
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomTypeDao {
    @Query("SELECT * FROM RoomType")
    fun getAllRoomTypes(): Flow<List<RoomType>>

    @Insert
    suspend fun insertRoomType(roomType: RoomType): Long

    @Query("SELECT * FROM RoomType WHERE roomTypeId = :roomTypeId")
    fun getRoomTypeById(roomTypeId: Int): Flow<RoomType>

    @Query("DELETE FROM RoomType WHERE roomTypeId = :roomTypeId")
    suspend fun deleteRoomTypeById(roomTypeId: Int)
}