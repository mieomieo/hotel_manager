package com.example.hotelmanagement.ui.roomtype

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelmanagement.R
import com.example.hotelmanagement.data.model.RoomFacility
import com.example.hotelmanagement.databinding.ItemRoomTypeDetailFacilitiesBinding

class RoomTypeDetailFacilitiesAdapter : RecyclerView.Adapter<RoomTypeDetailFacilitiesAdapter.ViewHolder>() {

    private var facilities: List<RoomFacility> = emptyList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemRoomTypeDetailFacilitiesBinding = ItemRoomTypeDetailFacilitiesBinding.bind(view)
        val facilityName: TextView = binding.tvFacilityName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_room_type_detail_facilities, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.facilityName.text = facilities[position].facilityName
    }

    override fun getItemCount(): Int = facilities.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newFacilities: List<RoomFacility>) {
        this.facilities = newFacilities
        notifyDataSetChanged()
    }
}