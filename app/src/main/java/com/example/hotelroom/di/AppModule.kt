package com.example.hotelroom.di

import android.content.Context
import androidx.room.Room
import com.example.hotelroom.database.AppDatabase
import com.example.hotelroom.database.dao.ClientDao
import com.example.hotelroom.database.dao.ReservationDao
import com.example.hotelroom.database.dao.RoomDao
import com.example.hotelroom.database.dao.RoomFacilityDao
import com.example.hotelroom.database.dao.RoomPhotoDao
import com.example.hotelroom.database.dao.RoomTypeDao
import com.example.hotelroom.database.dao.RoomTypeHasRoomFacilityDao
import com.example.hotelroom.database.repository.HotelRepository
import com.example.hotelroom.database.repository.HotelRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "hotel_database"
        ).createFromAsset("databases/hotel_database.db").build()
    }

    @Provides
    @Singleton
    fun provideClientDao(appDatabase: AppDatabase) = appDatabase.clientDao()

    @Provides
    @Singleton
    fun provideReservationDao(appDatabase: AppDatabase) = appDatabase.reservationDao()

    @Provides
    @Singleton
    fun provideRoomDao(appDatabase: AppDatabase) = appDatabase.roomDao()

    @Provides
    @Singleton
    fun provideRoomFacilityDao(appDatabase: AppDatabase) = appDatabase.roomFacilityDao()

    @Provides
    @Singleton
    fun provideRoomPhotoDao(appDatabase: AppDatabase) = appDatabase.roomPhotoDao()

    @Provides
    @Singleton
    fun provideRoomTypeDao(appDatabase: AppDatabase) = appDatabase.roomTypeDao()

    @Provides
    @Singleton
    fun provideRoomTypeHasRoomFacilityDao(appDatabase: AppDatabase) =
        appDatabase.roomTypeHasRoomFacilityDao()

    @Provides
    @Singleton
    fun provideHotelRepository(
        clientDao: ClientDao,
        reservationDao: ReservationDao,
        roomDao: RoomDao,
        roomFacilityDao: RoomFacilityDao,
        roomPhotoDao: RoomPhotoDao,
        roomTypeDao: RoomTypeDao,
        roomTypeHasRoomFacilityDao: RoomTypeHasRoomFacilityDao
    ): HotelRepository {
        return HotelRepositoryImpl(
            clientDao,
            reservationDao,
            roomDao,
            roomFacilityDao,
            roomPhotoDao,
            roomTypeDao,
            roomTypeHasRoomFacilityDao
        )
    }
}