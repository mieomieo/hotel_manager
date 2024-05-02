package com.example.hotelmanagement.ui.roomtype

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotelmanagement.R
import com.example.hotelmanagement.data.model.RoomPhoto
import com.example.hotelmanagement.databinding.ItemRoomTypeDetailPhotosBinding

class RoomTypeDetailPhotosAdapter : RecyclerView.Adapter<RoomTypeDetailPhotosAdapter.ViewHolder>() {

    private var photos: List<RoomPhoto> = emptyList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemRoomTypeDetailPhotosBinding =
            ItemRoomTypeDetailPhotosBinding.bind(view)
        val roomTypePhoto: ImageView = binding.imgRoomTypeDetailPhotos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_room_type_detail_photos, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.roomTypePhoto.context).load(photos[position].photoData).error(R.drawable.image_no_available)
            .into(holder.roomTypePhoto)
    }

    override fun getItemCount(): Int = photos.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newPhotos: List<RoomPhoto>) {
        this.photos = newPhotos
        notifyDataSetChanged()
    }
}