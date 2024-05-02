package com.example.hotelroom.ui.roomtype

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelroom.R
import com.example.hotelroom.databinding.FragmentRoomTypeDetailBinding
import com.example.hotelroom.utils.toVietnameseCurrency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomTypeDetailFragment : Fragment() {
    private val args: RoomTypeDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentRoomTypeDetailBinding
    private var roomTypeId: Int = 0
    private val viewModel: RoomTypeViewModel by viewModels()
    private lateinit var roomTypeDetailPhotosAdapter: RoomTypeDetailPhotosAdapter
    private lateinit var roomTypeDetailFacilitiesAdapter: RoomTypeDetailFacilitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomTypeId = args.roomTypeId
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomTypeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.getRoomTypeDetail(roomTypeId)
        viewModel.getRoomFacilitiesByRoomTypeId(roomTypeId)
        viewModel.getRoomPhotosByRoomTypeId(roomTypeId)
        observeViewModel()

        binding.btnDelete.setOnClickListener {
            viewModel.deleteRoomTypeAndPhotos(roomTypeId)
            findNavController().navigate(R.id.roomTypeFragment)
        }

        binding.btnReturn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        roomTypeDetailPhotosAdapter = RoomTypeDetailPhotosAdapter()
        roomTypeDetailFacilitiesAdapter = RoomTypeDetailFacilitiesAdapter()

        binding.rcvRoomTypeFacilities.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = roomTypeDetailFacilitiesAdapter
        }

        binding.rcvRoomTypePhotos.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = roomTypeDetailPhotosAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.roomType.observe(viewLifecycleOwner) { roomType ->
            binding.tvRoomTypeName.text = roomType?.roomTypeName
            binding.tvRoomTypePrice.text = roomType?.roomPrice?.toVietnameseCurrency()
        }

        viewModel.roomFacilities.observe(viewLifecycleOwner) { roomFacilities ->
            roomTypeDetailFacilitiesAdapter.submitList(roomFacilities)
        }

        viewModel.roomPhotos.observe(viewLifecycleOwner) { roomPhotos ->
            roomTypeDetailPhotosAdapter.submitList(roomPhotos)
        }
    }
}