package com.example.hotelmanagement.ui.facility

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelmanagement.R
import com.example.hotelmanagement.data.model.RoomFacility
import com.example.hotelmanagement.databinding.FragmentFacilityBinding
import com.example.hotelmanagement.interfaces.IOnItemClickListener
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FacilityFragment : Fragment(), IOnItemClickListener {
    private lateinit var binding: FragmentFacilityBinding
    private val viewModel: FacilityViewModel by viewModels()
    private lateinit var facilityAdapter: FacilityAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFacilityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.btnCreateFacility.setOnClickListener {
            showCreateFacilityDialog()
        }
    }

    private fun setupRecyclerView() {
        facilityAdapter = FacilityAdapter(this)
        binding.rcvListFacility.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvListFacility.adapter = facilityAdapter
    }

    private fun observeViewModel() {
        viewModel.facilities.observe(viewLifecycleOwner) { facilities ->
            facilityAdapter.submitList(facilities)
        }
    }

    private fun showCreateFacilityDialog() {
        val dialogCreateFacility = Dialog(requireContext())
        dialogCreateFacility.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogCreateFacility.setContentView(R.layout.dialog_add_update_facility)
        val window = dialogCreateFacility.window ?: return
        window.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val btnSubmit = dialogCreateFacility.findViewById<Button>(R.id.btn_submit)
        val btnCancel = dialogCreateFacility.findViewById<Button>(R.id.btn_cancel)

        btnSubmit.setOnClickListener {
            val facilityName =
                dialogCreateFacility.findViewById<TextInputEditText>(R.id.et_facility_name).text.toString()
            if (facilityName.isNotEmpty()) {
                val facility = RoomFacility(0, facilityName)
                viewModel.insertFacility(facility)
                dialogCreateFacility.dismiss()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.facility_name_cannot_be_empty),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        btnCancel.setOnClickListener {
            dialogCreateFacility.dismiss()
        }
        dialogCreateFacility.show()
    }

    private fun showEditFacilityDialog(facility: RoomFacility) {
        val dialogEditFacility = Dialog(requireContext())
        dialogEditFacility.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogEditFacility.setContentView(R.layout.dialog_add_update_facility)
        val window = dialogEditFacility.window ?: return
        window.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialogEditFacility.findViewById<TextView>(R.id.tv_create_facility).text =
            getString(R.string.update_facility)

        val btnSubmit = dialogEditFacility.findViewById<Button>(R.id.btn_submit)
        val btnCancel = dialogEditFacility.findViewById<Button>(R.id.btn_cancel)

        dialogEditFacility.findViewById<TextInputEditText>(R.id.et_facility_name)
            .setText(facility.facilityName)

        btnSubmit.setOnClickListener {
            val facilityName =
                dialogEditFacility.findViewById<TextInputEditText>(R.id.et_facility_name).text.toString()
            if (facilityName.isNotEmpty()) {
                val updatedFacility = RoomFacility(facility.roomFacilityId, facilityName)
                viewModel.updateFacility(updatedFacility)
                dialogEditFacility.dismiss()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.facility_name_cannot_be_empty),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        btnCancel.setOnClickListener {
            dialogEditFacility.dismiss()
        }
        dialogEditFacility.show()
    }

    override fun <T> onItemClick(item: T, isLongClick: Boolean, view: View) {
        if (item is RoomFacility) {
            if (isLongClick) {
                val popupMenu = PopupMenu(requireContext(), view)
                popupMenu.menuInflater.inflate(R.menu.menu_popup, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.action_delete -> {
                            viewModel.deleteFacility(item)
                            true
                        }

                        R.id.action_edit -> {
                            showEditFacilityDialog(item)
                            true
                        }

                        else -> false
                    }
                }
                popupMenu.show()
            }
        }
    }
}