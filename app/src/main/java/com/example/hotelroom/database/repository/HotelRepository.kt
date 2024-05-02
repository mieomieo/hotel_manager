package com.example.hotelroom.database.repository

import com.example.hotelroom.database.model.Client
import com.example.hotelroom.database.model.ClientExpenses
import com.example.hotelroom.database.model.Reservation
import com.example.hotelroom.database.model.ReservationWithClientRoomAndRoomType
import com.example.hotelroom.database.model.Room
import com.example.hotelroom.database.model.RoomFacility
import com.example.hotelroom.database.model.RoomPhoto
import com.example.hotelroom.database.model.RoomType
import com.example.hotelroom.database.model.RoomWithRoomType
import com.example.hotelroom.database.model.YearlyRevenue
import kotlinx.coroutines.flow.Flow

interface HotelRepository {
    fun getAllFacilities(): Flow<List<RoomFacility>>
    suspend fun insertFacility(facility: RoomFacility)
    suspend fun updateFacility(facility: RoomFacility)
    suspend fun deleteFacility(facility: RoomFacility)
    fun getAllClients(): Flow<List<Client>>
    suspend fun insertClient(client: Client)
    suspend fun updateClient(client: Client)
    suspend fun deleteClient(client: Client)
    fun getAllRoomTypes(): Flow<List<RoomType>>
    fun getDefaultRoomPhoto(roomTypeId: Int): Flow<RoomPhoto>
    suspend fun insertRoomTypeWithFacilitiesAndPhotos(
        roomType: RoomType, facilities: List<RoomFacility>, photos: List<RoomPhoto>
    )

    fun getRoomTypeById(roomTypeId: Int): Flow<RoomType>
    fun getRoomFacilitiesByRoomTypeId(roomTypeId: Int): Flow<List<RoomFacility>>
    fun getRoomPhotosByRoomTypeId(roomTypeId: Int): Flow<List<RoomPhoto>>
    suspend fun deleteRoomTypeAndPhotos(roomTypeId: Int)
    fun getRoomsWithRoomType(): Flow<List<RoomWithRoomType>>
    suspend fun insertRoom(room: Room)
    suspend fun deleteRoom(roomId: Int)
    suspend fun insertReservation(reservation: Reservation)
    fun getAllReservations(): Flow<List<Reservation>>
    suspend fun deleteReservation(reservationId: Int)
    fun getReservationWithClientRoomAndRoomType(): Flow<List<ReservationWithClientRoomAndRoomType>>
    fun getYearlyRevenue(): Flow<List<YearlyRevenue>>
    fun getTopClientsByExpensesIn2023(): Flow<List<ClientExpenses>>
}