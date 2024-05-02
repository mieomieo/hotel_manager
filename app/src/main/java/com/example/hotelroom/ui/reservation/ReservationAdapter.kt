package com.example.hotelmanagement.ui.reservation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelmanagement.R
import com.example.hotelmanagement.data.model.ReservationWithClientRoomAndRoomType
import com.example.hotelmanagement.databinding.ItemReservationBinding
import com.example.hotelmanagement.interfaces.IOnItemClickListener
import com.example.hotelmanagement.utils.convertTimestampToDateTime
import com.example.hotelmanagement.utils.convertTimestampToDay
import com.example.hotelmanagement.utils.convertTimestampToMonth
import com.example.hotelmanagement.utils.convertTimestampToYear
import com.example.hotelmanagement.utils.toVietnameseCurrency

class ReservationAdapter(
    private val listener: IOnItemClickListener
) : RecyclerView.Adapter<ReservationAdapter.ViewHolder>() {

    private var reservations: List<ReservationWithClientRoomAndRoomType> = emptyList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemReservationBinding = ItemReservationBinding.bind(view)
        val clientName: TextView = binding.tvClientName
        val roomPrice: TextView = binding.tvPrice
        val roomNumber: TextView = binding.tvRoomNumber
        val dateCheckIn: TextView = binding.tvDateCheckIn
        val dateCheckOut: TextView = binding.tvDateTimeCheckOut
        val monthYearCheckIn: TextView = binding.tvMonthYearCheckIn
        val additionalExpenses: TextView = binding.tvAdditionalExpenses
        val imgIcon: ImageView = binding.imageViewIcon
        val imgDelete: ImageView = binding.imgDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_reservation, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.clientName.text = reservations[position].client.name
        holder.roomPrice.text = reservations[position].roomType.roomPrice.toVietnameseCurrency()
        holder.imgIcon.setImageResource(R.drawable.ic_ticket)
        holder.roomNumber.text = getString(
            holder.itemView.context, R.string.room
        ) + " " + reservations[position].room.roomNumber.toString()
        holder.dateCheckIn.text =
            reservations[position].reservation.checkInDate.convertTimestampToDay()
        holder.dateCheckOut.text =
            reservations[position].reservation.checkOutDate.convertTimestampToDateTime()
        holder.monthYearCheckIn.text =
            reservations[position].reservation.checkInDate.convertTimestampToMonth() + "/" + reservations[position].reservation.checkInDate.convertTimestampToYear()
        holder.additionalExpenses.text =
            "+" + reservations[position].reservation.additionalExpenses.toVietnameseCurrency()
        holder.imgDelete.setOnClickListener {
            listener.onItemClick(reservations[position], false, it)
        }
    }

    override fun getItemCount(): Int = reservations.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newReservations: List<ReservationWithClientRoomAndRoomType>) {
        this.reservations = newReservations
        notifyDataSetChanged()
    }
}