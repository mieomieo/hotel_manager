package com.example.hotelroom.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Client(
    @PrimaryKey(autoGenerate = true)
    val clientId:Int,
    val name:String,
    val gender:String,
    val country:String,
    val phoneNumber: String
)