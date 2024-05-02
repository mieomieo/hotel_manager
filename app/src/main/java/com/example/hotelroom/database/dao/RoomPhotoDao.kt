package com.example.hotelroom.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hotelroom.database.model.RoomPhoto
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomPhotoDao {
    @Query("SELECT * FROM RoomPhoto WHERE roomTypeId = :roomTypeId AND isDefault = 1")
    fun getDefaultRoomPhotoByRoomTypeId(roomTypeId: Int): Flow<RoomPhoto>

    @Query("SELECT * FROM RoomPhoto WHERE roomTypeId = :roomTypeId")
    fun getRoomPhotosByRoomTypeId(roomTypeId: Int): Flow<List<RoomPhoto>>

    @Insert
    suspend fun insertRoomPhoto(roomPhoto: RoomPhoto)

    @Query("DELETE FROM RoomPhoto WHERE roomTypeId = :roomTypeId")
    suspend fun deletePhotosByRoomTypeId(roomTypeId: Int)
}