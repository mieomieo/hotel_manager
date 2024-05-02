package com.example.hotelroom.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.hotelroom.database.model.RoomFacility
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomFacilityDao {
    @Query("SELECT * FROM RoomFacility")
    fun getAllFacilities(): Flow<List<RoomFacility>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFacility(facility: RoomFacility)

    @Update
    suspend fun updateFacility(facility: RoomFacility)

    @Delete
    suspend fun deleteFacility(facility: RoomFacility)

    @Query("SELECT * FROM RoomFacility WHERE roomFacilityId IN (SELECT roomFacilityId FROM RoomTypeHasRoomFacility WHERE roomTypeId = :roomTypeId)")
    fun getRoomFacilitiesByRoomTypeId(roomTypeId: Int): Flow<List<RoomFacility>>
}