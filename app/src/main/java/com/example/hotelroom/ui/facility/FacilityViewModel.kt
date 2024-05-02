package com.example.hotelroom.ui.facility

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelroom.database.model.RoomFacility
import com.example.hotelroom.database.repository.HotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FacilityViewModel @Inject constructor(
    private val hotelRepository: HotelRepository
) : ViewModel() {

    private val _facilities: MutableLiveData<List<RoomFacility>> = MutableLiveData()
    val facilities: MutableLiveData<List<RoomFacility>> get() = _facilities

    init {
        fetchFacilities()
    }

    private fun fetchFacilities() {
        viewModelScope.launch {
            hotelRepository.getAllFacilities().collect { facilities ->
                _facilities.value = facilities
            }
        }
    }

    fun insertFacility(facility: RoomFacility) {
        viewModelScope.launch {
            hotelRepository.insertFacility(facility)
        }
    }

    fun updateFacility(facility: RoomFacility): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            try {
                hotelRepository.updateFacility(facility)
                result.postValue(true)
            } catch (e: Exception) {
                result.postValue(false)
            }
        }
        return result
    }

    fun deleteFacility(facility: RoomFacility) {
        viewModelScope.launch {
            hotelRepository.deleteFacility(facility)
        }
    }
}