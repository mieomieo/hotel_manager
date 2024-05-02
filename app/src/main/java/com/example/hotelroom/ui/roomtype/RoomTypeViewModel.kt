package com.example.hotelmanagement.ui.roomtype

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelmanagement.data.model.RoomFacility
import com.example.hotelmanagement.data.model.RoomPhoto
import com.example.hotelmanagement.data.model.RoomType
import com.example.hotelmanagement.data.model.RoomTypeWithDefaultImage
import com.example.hotelmanagement.data.repository.HotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomTypeViewModel @Inject constructor(
    private val hotelRepository: HotelRepository
) : ViewModel() {
    private var _roomTypesWithDefaultImage: MutableLiveData<List<RoomTypeWithDefaultImage>> =
        MutableLiveData()
    val roomTypesWithDefaultImage get() = _roomTypesWithDefaultImage

    private var _roomType: MutableLiveData<RoomType> = MutableLiveData()
    val roomType get() = _roomType

    private var _roomFacilities: MutableLiveData<List<RoomFacility>> = MutableLiveData()
    val roomFacilities get() = _roomFacilities

    private var _roomPhotos: MutableLiveData<List<RoomPhoto>> = MutableLiveData()
    val roomPhotos get() = _roomPhotos

    fun getAllRoomTypesWithDefaultImage() {
        viewModelScope.launch {
            hotelRepository.getAllRoomTypes().collect { roomTypes ->
                val roomTypesWithDefaultImage = roomTypes.map { roomType ->
                    val defaultImage =
                        hotelRepository.getDefaultRoomPhoto(roomType.roomTypeId).first()
                    RoomTypeWithDefaultImage(roomType, defaultImage)
                }
                _roomTypesWithDefaultImage.value = roomTypesWithDefaultImage
            }
        }
    }

    fun getRoomTypeDetail(roomTypeId: Int) {
        viewModelScope.launch {
            hotelRepository.getRoomTypeById(roomTypeId).collect {
                _roomType.value = it
            }
        }
    }

    fun getRoomFacilitiesByRoomTypeId(roomTypeId: Int) {
        viewModelScope.launch {
            hotelRepository.getRoomFacilitiesByRoomTypeId(roomTypeId).collect {
                _roomFacilities.value = it
            }
        }
    }

    fun getRoomPhotosByRoomTypeId(roomTypeId: Int) {
        viewModelScope.launch {
            hotelRepository.getRoomPhotosByRoomTypeId(roomTypeId).collect {
                _roomPhotos.value = it
            }
        }
    }

    fun insertRoomTypeWithFacilitiesAndPhotos(
        roomType: RoomType, facilities: List<RoomFacility>, photos: List<RoomPhoto>
    ) {
        viewModelScope.launch {
            hotelRepository.insertRoomTypeWithFacilitiesAndPhotos(roomType, facilities, photos)
        }
    }

    fun deleteRoomTypeAndPhotos(roomTypeId: Int) {
        viewModelScope.launch {
            hotelRepository.deleteRoomTypeAndPhotos(roomTypeId)
        }
    }
}