package com.example.hotelmanagement.ui.roomtype

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelmanagement.R
import com.example.hotelmanagement.data.model.RoomFacility
import com.example.hotelmanagement.data.model.RoomPhoto
import com.example.hotelmanagement.data.model.RoomType
import com.example.hotelmanagement.databinding.FragmentAddUpdateRoomTypeBinding
import com.example.hotelmanagement.interfaces.IOnItemClickListener
import com.example.hotelmanagement.ui.facility.FacilityAdapter
import com.example.hotelmanagement.ui.facility.FacilityViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUpdateRoomTypeFragment : Fragment(), IOnItemClickListener {
    private lateinit var binding: FragmentAddUpdateRoomTypeBinding
    private lateinit var facilityAdapter: FacilityPhotoAdapter<RoomFacility>
    private lateinit var photoAdapter: FacilityPhotoAdapter<RoomPhoto>
    private val roomTypeViewModel: RoomTypeViewModel by viewModels()
    private val facilityViewModel: FacilityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddUpdateRoomTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.btnAddFacility.setOnClickListener {
            showAddFacilityDialog()
        }

        binding.btnAddPhoto.setOnClickListener {
            showAddPhotoDialog()
        }

        binding.btnSubmit.setOnClickListener {
            val roomTypeName = binding.etRoomTypeName.text.toString()
            val roomTypePriceString = binding.etRoomTypePrice.text.toString()

            if (roomTypeName.isNotEmpty() && roomTypePriceString.isNotEmpty()) {
                val roomTypePrice = roomTypePriceString.toDouble()
                val roomTypeFacilities = facilityAdapter.getItems()
                val roomTypePhotos = photoAdapter.getItems()

                if (roomTypePrice > 0) {
                    val roomType = RoomType(
                        0, roomTypeName, roomTypePrice
                    )
                    roomTypeViewModel.insertRoomTypeWithFacilitiesAndPhotos(
                        roomType, roomTypeFacilities, roomTypePhotos
                    )
                    Toast.makeText(
                        requireContext(), getString(R.string.room_type_added), Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(), getString(R.string.fill_all_fields), Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(), getString(R.string.fill_all_fields), Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.roomTypeFragment)
        }
    }

    private fun setupRecyclerView() {
        facilityAdapter = FacilityPhotoAdapter { position -> facilityAdapter.removeItem(position) }
        photoAdapter = FacilityPhotoAdapter { position -> photoAdapter.removeItem(position) }

        binding.rvFacilities.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = facilityAdapter
        }
        binding.rvPhotos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = photoAdapter
        }
    }

    private fun showAddFacilityDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_list_facilities)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            dialog.dismiss()
        }

        val listAddFacilityAdapter = FacilityAdapter(this)
        dialog.findViewById<RecyclerView>(R.id.rcv_list_facility).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAddFacilityAdapter
        }
        facilityViewModel.facilities.observe(viewLifecycleOwner) { facilities ->
            listAddFacilityAdapter.submitList(facilities)
        }

        dialog.show()
    }

    private fun showAddPhotoDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_add_photo)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btn_submit).setOnClickListener {
            val photoUrl = dialog.findViewById<TextInputEditText>(R.id.et_photo_url).text.toString()
            photoAdapter.addItem(RoomPhoto(0, 0, photoUrl, false))
            Toast.makeText(
                requireContext(), getString(R.string.photo_added), Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun <T> onItemClick(item: T, isLongClick: Boolean, view: View) {
        if (item is RoomFacility) {
            if (!isLongClick) {
                facilityAdapter.addItem(item)
                Toast.makeText(
                    requireContext(), getString(R.string.facility_added), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}