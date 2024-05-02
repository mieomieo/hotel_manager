package com.example.hotelmanagement.ui.room

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelmanagement.R
import com.example.hotelmanagement.data.model.Room
import com.example.hotelmanagement.data.model.RoomTypeWithDefaultImage
import com.example.hotelmanagement.data.model.RoomWithRoomType
import com.example.hotelmanagement.databinding.FragmentRoomBinding
import com.example.hotelmanagement.interfaces.IOnItemClickListener
import com.example.hotelmanagement.ui.roomtype.RoomTypeViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomFragment : Fragment(), IOnItemClickListener {
    private lateinit var binding: FragmentRoomBinding
    private lateinit var roomAdapter: RoomAdapter
    private val roomViewModel: RoomViewModel by viewModels()
    private val roomTypeViewModel: RoomTypeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roomTypeViewModel.getAllRoomTypesWithDefaultImage()
        setupRecyclerView()
        observerViewModel()

        binding.btnCreateRoom.setOnClickListener {
            showCreateRoomDialog()
        }
    }

    private fun setupRecyclerView() {
        roomAdapter = RoomAdapter(this)
        binding.rcvListRooms.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = roomAdapter
        }
    }

    private fun observerViewModel() {
        roomViewModel.roomsWithRoomType.observe(viewLifecycleOwner) { roomsWithRoomType ->
            roomAdapter.submitList(roomsWithRoomType)
        }
    }


    private fun showCreateRoomDialog() {
        val dialogCreateRoom = Dialog(requireContext())
        dialogCreateRoom.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogCreateRoom.setContentView(R.layout.dialog_add_update_room)
        val window = dialogCreateRoom.window ?: return
        window.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val spRoomType = dialogCreateRoom.findViewById<Spinner>(R.id.sp_room_type)

        roomTypeViewModel.roomTypesWithDefaultImage.observe(viewLifecycleOwner) { roomTypes ->
            val roomTypeNames = roomTypes.map { RoomTypeItem(it) }
            val adapter = ArrayAdapter(
                requireContext(), android.R.layout.simple_spinner_item, roomTypeNames
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

            spRoomType.adapter = adapter
        }

        val btnSubmit = dialogCreateRoom.findViewById<Button>(R.id.btn_submit)
        val btnCancel = dialogCreateRoom.findViewById<Button>(R.id.btn_cancel)

        btnSubmit.setOnClickListener {
            val roomNumber =
                dialogCreateRoom.findViewById<TextInputEditText>(R.id.et_room_number).text.toString()
            val roomTypeId =
                (spRoomType.selectedItem as RoomTypeItem).roomTypeWithDefaultImage.roomType.roomTypeId

            if (roomNumber.isNotEmpty()) {
                val room = Room(0, roomNumber.toInt(), roomTypeId)
                roomViewModel.insertRoom(room)
                dialogCreateRoom.dismiss()
            } else {
                Toast.makeText(
                    requireContext(), getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT
                ).show()
            }
        }
        btnCancel.setOnClickListener {
            dialogCreateRoom.dismiss()
        }
        dialogCreateRoom.show()
    }

    data class RoomTypeItem(val roomTypeWithDefaultImage: RoomTypeWithDefaultImage) {
        override fun toString(): String {
            return roomTypeWithDefaultImage.roomType.roomTypeName
        }
    }

    override fun <T> onItemClick(item: T, isLongClick: Boolean, view: View) {
        when (view.id) {
            R.id.img_delete -> {
                val room = item as RoomWithRoomType
                roomViewModel.deleteRoom(room.room.roomId)
                Toast.makeText(requireContext(), "Room deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}