package com.example.hotelmanagement.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelmanagement.R
import com.example.hotelmanagement.data.model.YearlyRevenue
import com.example.hotelmanagement.databinding.ItemYearlyRevenueBinding
import com.example.hotelmanagement.utils.toVietnameseCurrency

class YearlyRevenueAdapter : RecyclerView.Adapter<YearlyRevenueAdapter.ViewHolder>() {

    private var yearlyRevenue: List<YearlyRevenue> = emptyList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemYearlyRevenueBinding = ItemYearlyRevenueBinding.bind(view)
        val year = binding.tvYear
        val revenue = binding.tvRevenue
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_yearly_revenue, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.year.text = yearlyRevenue[position].year.toString()
        holder.revenue.text = yearlyRevenue[position].revenue.toVietnameseCurrency()
    }

    override fun getItemCount(): Int = yearlyRevenue.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(listUpdated: List<YearlyRevenue>) {
        this.yearlyRevenue = listUpdated
        notifyDataSetChanged()
    }
}