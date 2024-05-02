package com.example.hotelroom.database.repository

import android.util.Log
import com.example.hotelroom.database.dao.ClientDao
import com.example.hotelroom.database.dao.ReservationDao
import com.example.hotelroom.database.dao.RoomDao
import com.example.hotelroom.database.dao.RoomFacilityDao
import com.example.hotelroom.database.dao.RoomPhotoDao
import com.example.hotelroom.database.dao.RoomTypeDao
import com.example.hotelroom.database.dao.RoomTypeHasRoomFacilityDao
import com.example.hotelroom.database.model.Client
import com.example.hotelroom.database.model.ClientExpenses
import com.example.hotelroom.database.model.Reservation
import com.example.hotelroom.database.model.ReservationWithClientRoomAndRoomType
import com.example.hotelroom.database.model.Room
import com.example.hotelroom.database.model.RoomFacility
import com.example.hotelroom.database.model.RoomPhoto
import com.example.hotelroom.database.model.RoomType
import com.example.hotelroom.database.model.RoomTypeHasRoomFacility
import com.example.hotelroom.database.model.RoomWithRoomType
import com.example.hotelroom.database.model.YearlyRevenue
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HotelRepositoryImpl @Inject constructor(
    private val clientDao: ClientDao,
    private val reservationDao: ReservationDao,
    private val roomDao: RoomDao,
    private val roomFacilityDao: RoomFacilityDao,
    private val roomPhotoDao: RoomPhotoDao,
    private val roomTypeDao: RoomTypeDao,
    private val roomTypeHasRoomFacilityDao: RoomTypeHasRoomFacilityDao
) : HotelRepository {
    override fun getAllFacilities(): Flow<List<RoomFacility>> {
        return roomFacilityDao.getAllFacilities()
    }

    override suspend fun insertFacility(facility: RoomFacility) {
        roomFacilityDao.insertFacility(facility)
    }

    override suspend fun updateFacility(facility: RoomFacility) {
        roomFacilityDao.updateFacility(facility)
    }

    override suspend fun deleteFacility(facility: RoomFacility) {
        roomFacilityDao.deleteFacility(facility)
    }

    override fun getAllClients(): Flow<List<Client>> {
        return clientDao.getAllClients()
    }

    override suspend fun insertClient(client: Client) {
        clientDao.insertClient(client)
    }

    override suspend fun updateClient(client: Client) {
        clientDao.updateClient(client)
    }

    override suspend fun deleteClient(client: Client) {
        clientDao.deleteClient(client)
    }


    override fun getAllRoomTypes(): Flow<List<RoomType>> {
        return roomTypeDao.getAllRoomTypes()
    }

    override fun getDefaultRoomPhoto(roomTypeId: Int): Flow<RoomPhoto> {
        return roomPhotoDao.getDefaultRoomPhotoByRoomTypeId(roomTypeId)
    }

    override suspend fun insertRoomTypeWithFacilitiesAndPhotos(
        roomType: RoomType, facilities: List<RoomFacility>, photos: List<RoomPhoto>
    ) {
        try {
            // Step 1: Insert the RoomType
            val roomTypeId = roomTypeDao.insertRoomType(roomType)

            // Step 2: Insert the RoomTypeHasRoomFacility for each facility
            for (facility in facilities) {
                val roomTypeHasRoomFacility =
                    RoomTypeHasRoomFacility(roomTypeId.toInt(), facility.roomFacilityId)
                roomTypeHasRoomFacilityDao.insertRoomTypeHasRoomFacility(roomTypeHasRoomFacility)
            }

            // Step 3: Insert the RoomPhoto for each photo
            for ((index, photo) in photos.withIndex()) {
                val isDefault = index == 0
                val roomPhoto = RoomPhoto(0, roomTypeId.toInt(), photo.photoData, isDefault)
                roomPhotoDao.insertRoomPhoto(roomPhoto)
            }
        } catch (e: Exception) {
            Log.e("HotelRepositoryImpl", "Error inserting RoomType with facilities and photos", e)
        }
    }

    override fun getRoomTypeById(roomTypeId: Int): Flow<RoomType> {
        return roomTypeDao.getRoomTypeById(roomTypeId)
    }

    override fun getRoomFacilitiesByRoomTypeId(roomTypeId: Int): Flow<List<RoomFacility>> {
        return roomFacilityDao.getRoomFacilitiesByRoomTypeId(roomTypeId)
    }

    override fun getRoomPhotosByRoomTypeId(roomTypeId: Int): Flow<List<RoomPhoto>> {
        return roomPhotoDao.getRoomPhotosByRoomTypeId(roomTypeId)
    }

    override suspend fun deleteRoomTypeAndPhotos(roomTypeId: Int) {
        roomPhotoDao.deletePhotosByRoomTypeId(roomTypeId)
        roomTypeHasRoomFacilityDao.deleteRoomTypeHasRoomFacility(roomTypeId)
        roomTypeDao.deleteRoomTypeById(roomTypeId)
    }

    override fun getRoomsWithRoomType(): Flow<List<RoomWithRoomType>> {
        return roomDao.getRoomsWithRoomType()
    }

    override suspend fun insertRoom(room: Room) {
        roomDao.insertRoom(room)
    }

    override suspend fun deleteRoom(roomId: Int) {
        roomDao.deleteRoom(roomId)
    }

    override suspend fun insertReservation(reservation: Reservation) {
        reservationDao.insertReservation(reservation)
    }

    override fun getAllReservations(): Flow<List<Reservation>> {
        return reservationDao.getAllReservations()
    }

    override suspend fun deleteReservation(reservationId: Int) {
        reservationDao.deleteReservation(reservationId)
    }

    override fun getReservationWithClientRoomAndRoomType(): Flow<List<ReservationWithClientRoomAndRoomType>> {
        return reservationDao.getReservationsWithClientRoomAndRoomType()
    }

    override fun getYearlyRevenue(): Flow<List<YearlyRevenue>> {
        return reservationDao.getYearlyRevenue()
    }

    override fun getTopClientsByExpensesIn2023(): Flow<List<ClientExpenses>> {
        return reservationDao.getTopClientsByExpensesIn2023()
    }
}