package com.example.hotelmanagement.ui.facility

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelmanagement.R
import com.example.hotelmanagement.data.model.RoomFacility
import com.example.hotelmanagement.databinding.ItemFacilityBinding
import com.example.hotelmanagement.interfaces.IOnItemClickListener

class FacilityAdapter(
    private val listener: IOnItemClickListener
) : RecyclerView.Adapter<FacilityAdapter.FacilityViewHolder>() {

    private var facilities: List<RoomFacility> = emptyList()

    class FacilityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemFacilityBinding = ItemFacilityBinding.bind(view)
        val facilityName: TextView = binding.tvFacilityName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_facility, parent, false)
        return FacilityViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FacilityViewHolder, position: Int) {
        holder.facilityName.text = facilities[position].facilityName
        holder.itemView.setOnClickListener {
            listener.onItemClick(facilities[position], false, it)
        }
        holder.itemView.setOnLongClickListener {
            listener.onItemClick(facilities[position], true, it)
            true
        }
    }

    override fun getItemCount(): Int = facilities.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newFacilities: List<RoomFacility>) {
        this.facilities = newFacilities
        notifyDataSetChanged()
    }
}