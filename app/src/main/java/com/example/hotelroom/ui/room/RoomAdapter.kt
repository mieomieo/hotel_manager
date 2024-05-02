package com.example.hotelmanagement.ui.room

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelmanagement.R
import com.example.hotelmanagement.data.model.RoomWithRoomType
import com.example.hotelmanagement.databinding.ItemRoomBinding
import com.example.hotelmanagement.interfaces.IOnItemClickListener

class RoomAdapter(
    private val listener: IOnItemClickListener
) : RecyclerView.Adapter<RoomAdapter.FacilityViewHolder>() {

    private var roomsWithRoomType: List<RoomWithRoomType> = emptyList()

    class FacilityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemRoomBinding = ItemRoomBinding.bind(view)
        val roomNumber: TextView = binding.tvRoomNumber
        val roomType: TextView = binding.tvRoomType
        val imgDelete: ImageView = binding.imgDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
        return FacilityViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FacilityViewHolder, position: Int) {
        holder.roomNumber.text = roomsWithRoomType[position].room.roomNumber.toString()
        holder.roomType.text = roomsWithRoomType[position].roomType.roomTypeName

        holder.imgDelete.setOnClickListener {
            listener.onItemClick(roomsWithRoomType[position], false, it)
        }
    }

    override fun getItemCount(): Int = roomsWithRoomType.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newRoomsWithRoomType: List<RoomWithRoomType>) {
        this.roomsWithRoomType = newRoomsWithRoomType
        notifyDataSetChanged()
    }
}