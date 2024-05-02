package com.example.hotelmanagement.ui.reservation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelmanagement.data.model.ClientExpenses
import com.example.hotelmanagement.data.model.Reservation
import com.example.hotelmanagement.data.model.ReservationWithClientRoomAndRoomType
import com.example.hotelmanagement.data.model.YearlyRevenue
import com.example.hotelmanagement.data.repository.HotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val hotelRepository: HotelRepository
) : ViewModel() {
    private var _reservationsWithClientRoomAndRoomType: MutableLiveData<List<ReservationWithClientRoomAndRoomType>> =
        MutableLiveData()
    val reservationsWithClientRoomAndRoomType get() = _reservationsWithClientRoomAndRoomType

    private var _yearlyRevenue: MutableLiveData<List<YearlyRevenue>> = MutableLiveData()
    val yearlyRevenue get() = _yearlyRevenue

    private var _topClientsByExpensesIn2023: MutableLiveData<List<ClientExpenses>> = MutableLiveData()
    val topClientsByExpensesIn2023 get() = _topClientsByExpensesIn2023

    init {
        getReservationsWithClientRoomAndRoomType()
    }

    private fun getReservationsWithClientRoomAndRoomType() {
        viewModelScope.launch {
            hotelRepository.getReservationWithClientRoomAndRoomType().collect {
                _reservationsWithClientRoomAndRoomType.value = it
            }
        }
    }

    fun deleteReservation(reservationId: Int) {
        viewModelScope.launch {
            hotelRepository.deleteReservation(reservationId)
        }
    }

    fun insertReservation(reservation: Reservation) {
        viewModelScope.launch {
            hotelRepository.insertReservation(reservation)
        }
    }

    fun getYearlyRevenue() {
        viewModelScope.launch {
            hotelRepository.getYearlyRevenue().collect {
                _yearlyRevenue.value = it
            }
        }
    }

    fun getTopClientsByExpensesIn2023() {
        viewModelScope.launch {
            hotelRepository.getTopClientsByExpensesIn2023().collect {
                _topClientsByExpensesIn2023.value = it
            }
        }
    }
}