package com.example.hotelroom.ui.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelroom.database.model.Client
import com.example.hotelroom.database.repository.HotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val hotelRepository: HotelRepository
) : ViewModel() {

    private val _clients: MutableLiveData<List<Client>> = MutableLiveData()
    val clients: MutableLiveData<List<Client>> get() = _clients

    init {
        fetchClients()
    }

    private fun fetchClients() {
        viewModelScope.launch {
            hotelRepository.getAllClients().collect { clients ->
                _clients.value = clients
            }
        }
    }

    fun insertClient(client: Client) {
        viewModelScope.launch {
            hotelRepository.insertClient(client)
        }
    }

    fun updateClient(client: Client): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            try {
                hotelRepository.updateClient(client)
                result.postValue(true)
            } catch (e: Exception) {
                result.postValue(false)
            }
        }
        return result
    }

    fun deleteClient(client: Client) {
        viewModelScope.launch {
            hotelRepository.deleteClient(client)
        }
    }
}