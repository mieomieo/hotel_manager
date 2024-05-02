package com.example.hotelroom.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hotelroom.database.dao.ClientDao
import com.example.hotelroom.database.dao.ReservationDao
import com.example.hotelroom.database.dao.RoomDao
import com.example.hotelroom.database.dao.RoomFacilityDao
import com.example.hotelroom.database.dao.RoomPhotoDao
import com.example.hotelroom.database.dao.RoomTypeDao
import com.example.hotelroom.database.dao.RoomTypeHasRoomFacilityDao
import com.example.hotelroom.database.model.Client
import com.example.hotelroom.database.model.Reservation
import com.example.hotelroom.database.model.Room
import com.example.hotelroom.database.model.RoomFacility
import com.example.hotelroom.database.model.RoomPhoto
import com.example.hotelroom.database.model.RoomType
import com.example.hotelroom.database.model.RoomTypeHasRoomFacility

@Database(
    entities = [Client::class, Reservation::class, Room::class, RoomFacility::class, RoomPhoto::class, RoomType::class, RoomTypeHasRoomFacility::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
    abstract fun reservationDao(): ReservationDao
    abstract fun roomDao(): RoomDao
    abstract fun roomFacilityDao(): RoomFacilityDao
    abstract fun roomPhotoDao(): RoomPhotoDao
    abstract fun roomTypeDao(): RoomTypeDao
    abstract fun roomTypeHasRoomFacilityDao(): RoomTypeHasRoomFacilityDao
}