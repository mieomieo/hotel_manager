package com.example.hotelmanagement.ui.roomtype

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hotelmanagement.data.model.RoomTypeWithDefaultImage
import com.example.hotelmanagement.databinding.FragmentRoomTypeBinding
import com.example.hotelmanagement.interfaces.IOnItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomTypeFragment : Fragment(), IOnItemClickListener {
    private lateinit var binding: FragmentRoomTypeBinding
    private lateinit var roomTypeAdapter: RoomTypeAdapter
    private val viewModel: RoomTypeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.getAllRoomTypesWithDefaultImage()

        observeViewModel()

        binding.btnCreateRoomType.setOnClickListener {
            findNavController().navigate(RoomTypeFragmentDirections.actionRoomTypeFragmentToAddUpdateRoomTypeFragment())
        }
    }

    private fun setupRecyclerView() {
        roomTypeAdapter = RoomTypeAdapter(this)
        binding.rcvListRoomType.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvListRoomType.adapter = roomTypeAdapter
    }

    private fun observeViewModel() {
        viewModel.roomTypesWithDefaultImage.observe(viewLifecycleOwner) { roomTypes ->
            roomTypeAdapter.submitList(roomTypes)
        }
    }

    override fun <T> onItemClick(item: T, isLongClick: Boolean, view: View) {
        if (!isLongClick) {
            val roomType = item as RoomTypeWithDefaultImage
            when (view.id) {
                else -> {
                    findNavController().navigate(
                        RoomTypeFragmentDirections.actionRoomTypeFragmentToRoomTypeDetailFragment(
                            roomType.roomType.roomTypeId
                        )
                    )
                }
            }
        }
    }
}