package com.example.hotelroom.ui.roomtype

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotelroom.R
import com.example.hotelroom.database.model.RoomTypeWithDefaultImage
import com.example.hotelroom.databinding.ItemRoomTypeBinding
import com.example.hotelroom.interfaces.IOnItemClickListener
import com.example.hotelroom.utils.toVietnameseCurrency

class RoomTypeAdapter(
    private val listener: IOnItemClickListener
) : RecyclerView.Adapter<RoomTypeAdapter.RoomTypeViewHolder>() {

    private var roomTypes: List<RoomTypeWithDefaultImage> = emptyList()

    class RoomTypeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemRoomTypeBinding = ItemRoomTypeBinding.bind(view)
        val roomTypeName: TextView = binding.tvRoomTypeName
        val roomTypePrice: TextView = binding.tvRoomTypePrice
        val roomTypeImage: ImageView = binding.imgRoomType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomTypeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_room_type, parent, false)
        return RoomTypeViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RoomTypeViewHolder, position: Int) {
        holder.roomTypeName.text = roomTypes[position].roomType.roomTypeName
        holder.roomTypePrice.text = roomTypes[position].roomType.roomPrice.toVietnameseCurrency()
        Glide.with(holder.itemView).load(roomTypes[position].defaultImage?.photoData)
            .error(R.drawable.image_no_available).into(holder.roomTypeImage)
        holder.itemView.setOnClickListener {
            listener.onItemClick(roomTypes[position], false, it)
        }
    }

    override fun getItemCount(): Int = roomTypes.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newRoomTypes: List<RoomTypeWithDefaultImage>) {
        this.roomTypes = newRoomTypes
        notifyDataSetChanged()
    }
}