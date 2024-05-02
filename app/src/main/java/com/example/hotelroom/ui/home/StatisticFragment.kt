package com.example.hotelroom.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelroom.databinding.FragmentStatisticBinding
import com.example.hotelroom.ui.reservation.ReservationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticFragment : Fragment() {
    private lateinit var binding: FragmentStatisticBinding
    private val viewModel: ReservationViewModel by viewModels()
    private lateinit var yearlyRevenueAdapter: YearlyRevenueAdapter
    private lateinit var topClientAdapter: TopClientAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.getYearlyRevenue()
        viewModel.getTopClientsByExpensesIn2023()
        observerViewModel()
    }

    private fun setupRecyclerView() {
        yearlyRevenueAdapter = YearlyRevenueAdapter()
        topClientAdapter = TopClientAdapter()

        binding.rcvTotalIncomeByYear.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = yearlyRevenueAdapter
        }
        binding.rcvTopClients2023.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = topClientAdapter
        }
    }

    private fun observerViewModel() {
        viewModel.yearlyRevenue.observe(viewLifecycleOwner) {
            yearlyRevenueAdapter.submitList(it)
        }
        viewModel.topClientsByExpensesIn2023.observe(viewLifecycleOwner) {
            topClientAdapter.submitList(it)
        }
    }
}