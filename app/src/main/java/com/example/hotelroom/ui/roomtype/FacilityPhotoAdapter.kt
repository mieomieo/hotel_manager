package com.example.hotelroom.ui.roomtype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelroom.R
import com.example.hotelroom.database.model.RoomFacility
import com.example.hotelroom.database.model.RoomPhoto

class FacilityPhotoAdapter<T>(
    private val onRemove: (Int) -> Unit
) : RecyclerView.Adapter<FacilityPhotoAdapter<T>.ViewHolder>() {

    private val items = mutableListOf<T>()

    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_add_facility_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.tv_name)
        private val imgRemove: ImageView = view.findViewById(R.id.img_remove)

        fun bind(item: T) {
            when (item) {
                is RoomFacility -> tvName.text = item.facilityName
                is RoomPhoto -> tvName.text = item.photoData
            }
            imgRemove.setOnClickListener { onRemove(adapterPosition) }
        }
    }

    fun getItems(): List<T> {
        return items
    }
}