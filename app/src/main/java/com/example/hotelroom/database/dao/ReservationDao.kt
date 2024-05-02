package com.example.hotelroom.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.hotelroom.database.model.ClientExpenses
import com.example.hotelroom.database.model.Reservation
import com.example.hotelroom.database.model.ReservationWithClientRoomAndRoomType
import com.example.hotelroom.database.model.YearlyRevenue
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {
    @Query("SELECT * FROM Reservation")
    fun getAllReservations(): Flow<List<Reservation>>

    @Insert
    suspend fun insertReservation(reservation: Reservation)

    @Query("DELETE FROM Reservation WHERE reservationId = :reservationId")
    suspend fun deleteReservation(reservationId: Int)

    @Transaction
    @Query("SELECT Reservation.reservationId as reservation_reservationId, Reservation.clientId as reservation_clientId, Reservation.roomId as reservation_roomId, Reservation.checkInDate as reservation_checkInDate, Reservation.checkOutDate as reservation_checkOutDate, Reservation.additionalExpenses as reservation_additionalExpenses, Client.clientId as client_clientId, Client.name as client_name, Client.gender as client_gender, Client.country as client_country, Client.phoneNumber as client_phoneNumber, Room.roomId as room_roomId, Room.roomNumber as room_roomNumber, Room.roomTypeId as room_roomTypeId, RoomType.roomTypeId as roomType_roomTypeId, RoomType.roomTypeName as roomType_roomTypeName, RoomType.roomPrice as roomType_roomPrice FROM Reservation INNER JOIN Client ON Reservation.clientId = Client.clientId INNER JOIN Room ON Reservation.roomId = Room.roomId INNER JOIN RoomType ON Room.roomTypeId = RoomType.roomTypeId")
    fun getReservationsWithClientRoomAndRoomType(): Flow<List<ReservationWithClientRoomAndRoomType>>

    @Query(
        """
        SELECT strftime('%Y', Reservation.checkInDate / 1000, 'unixepoch') as year, 
               SUM((julianday(Reservation.checkOutDate / 1000, 'unixepoch') - julianday(Reservation.checkInDate / 1000, 'unixepoch')) * RoomType.roomPrice + Reservation.additionalExpenses) as revenue
        FROM Reservation
        INNER JOIN Room ON Reservation.roomId = Room.roomId
        INNER JOIN RoomType ON Room.roomTypeId = RoomType.roomTypeId
        GROUP BY year
    """
    )
    fun getYearlyRevenue(): Flow<List<YearlyRevenue>>

    @Query(
        """
        SELECT Client.name as clientName,
               SUM((julianday(Reservation.checkOutDate / 1000, 'unixepoch') - julianday(Reservation.checkInDate / 1000, 'unixepoch')) * RoomType.roomPrice + Reservation.additionalExpenses) as expenses
        FROM Reservation
        INNER JOIN Client ON Reservation.clientId = Client.clientId
        INNER JOIN Room ON Reservation.roomId = Room.roomId
        INNER JOIN RoomType ON Room.roomTypeId = RoomType.roomTypeId
        WHERE strftime('%Y', Reservation.checkInDate / 1000, 'unixepoch') = '2023'
        GROUP BY Reservation.clientId
        ORDER BY expenses DESC
    """
    )
    fun getTopClientsByExpensesIn2023(): Flow<List<ClientExpenses>>
}