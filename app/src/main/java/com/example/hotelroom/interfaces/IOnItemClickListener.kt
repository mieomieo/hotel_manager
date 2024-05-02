package com.example.hotelroom.interfaces

import android.view.View

interface IOnItemClickListener {
    fun <T> onItemClick(item: T, isLongClick: Boolean, view: View)
}