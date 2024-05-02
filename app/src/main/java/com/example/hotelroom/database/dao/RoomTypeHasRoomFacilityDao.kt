package com.example.hotelroom.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hotelroom.database.model.RoomTypeHasRoomFacility

@Dao
interface RoomTypeHasRoomFacilityDao {
    @Insert
    suspend fun insertRoomTypeHasRoomFacility(roomTypeHasRoomFacility: RoomTypeHasRoomFacility)

    @Query("DELETE FROM RoomTypeHasRoomFacility WHERE roomTypeId = :roomTypeId")
    suspend fun deleteRoomTypeHasRoomFacility(roomTypeId: Int)
}