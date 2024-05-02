package com.example.hotelroom.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelroom.R
import com.example.hotelroom.database.model.ClientExpenses
import com.example.hotelroom.databinding.ItemTopClientBinding
import com.example.hotelroom.utils.toVietnameseCurrency

class TopClientAdapter : RecyclerView.Adapter<TopClientAdapter.ViewHolder>() {

    private var topClients: List<ClientExpenses> = emptyList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemTopClientBinding = ItemTopClientBinding.bind(view)
        val client = binding.tvClientName
        val expenses = binding.tvRevenue
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_top_client, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.client.text = topClients[position].clientName
        holder.expenses.text = topClients[position].expenses.toVietnameseCurrency()
    }

    override fun getItemCount(): Int = topClients.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(listUpdated: List<ClientExpenses>) {
        this.topClients = listUpdated
        notifyDataSetChanged()
    }
}