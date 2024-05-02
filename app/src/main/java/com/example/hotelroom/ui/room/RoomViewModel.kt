package com.example.hotelmanagement.ui.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelmanagement.data.model.Room
import com.example.hotelmanagement.data.model.RoomWithRoomType
import com.example.hotelmanagement.data.repository.HotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val hotelRepository: HotelRepository
) : ViewModel() {
    private var _roomsWithRoomType: MutableLiveData<List<RoomWithRoomType>> = MutableLiveData()
    val roomsWithRoomType: LiveData<List<RoomWithRoomType>> get() = _roomsWithRoomType

    init {
        fetchRoomsWithRoomType()
    }

    private fun fetchRoomsWithRoomType() {
        viewModelScope.launch {
            hotelRepository.getRoomsWithRoomType().collect {
                _roomsWithRoomType.value = it
            }
        }
    }

    fun insertRoom(room: Room) {
        viewModelScope.launch {
            hotelRepository.insertRoom(room)
        }
    }

    fun deleteRoom(roomId: Int) {
        viewModelScope.launch {
            hotelRepository.deleteRoom(roomId)
        }
    }
}