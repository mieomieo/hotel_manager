package com.example.hotelmanagement.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hotelmanagement.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llRoomFacility.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFacilityFragment())
        }

        binding.llClient.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToClientFragment())
        }

        binding.llRoomType.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToRoomTypeFragment())
        }

        binding.llRoom.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToRoomFragment())
        }

        binding.llReservation.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToReservationFragment())
        }
    }
}