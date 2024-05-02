package com.example.hotelmanagement.ui.reservation

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelmanagement.R
import com.example.hotelmanagement.data.model.Client
import com.example.hotelmanagement.data.model.Reservation
import com.example.hotelmanagement.data.model.ReservationWithClientRoomAndRoomType
import com.example.hotelmanagement.data.model.RoomWithRoomType
import com.example.hotelmanagement.databinding.FragmentReservationBinding
import com.example.hotelmanagement.interfaces.IOnItemClickListener
import com.example.hotelmanagement.ui.client.ClientViewModel
import com.example.hotelmanagement.ui.room.RoomViewModel
import com.example.hotelmanagement.utils.convertTimestampToDateTime
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

@AndroidEntryPoint
class ReservationFragment : Fragment(), IOnItemClickListener {
    private lateinit var binding: FragmentReservationBinding
    private lateinit var reservationAdapter: ReservationAdapter
    private val reservationViewModel: ReservationViewModel by viewModels()
    private val roomViewModel: RoomViewModel by viewModels()
    private val clientViewModel: ClientViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        observeViewModel()

        binding.btnCreateReservation.setOnClickListener {
            showCreateReservationDialog()
        }
    }

    private fun setupRecyclerView() {
        reservationAdapter = ReservationAdapter(this)
        binding.rcvListReservation.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), DividerItemDecoration.VERTICAL
                )
            )
            adapter = reservationAdapter
        }
    }

    private fun observeViewModel() {
        reservationViewModel.reservationsWithClientRoomAndRoomType.observe(viewLifecycleOwner) {
            reservationAdapter.submitList(it)
        }
    }

    override fun <T> onItemClick(item: T, isLongClick: Boolean, view: View) {
        when (view.id) {
            R.id.img_delete -> {
                val reservation = item as ReservationWithClientRoomAndRoomType
                reservationViewModel.deleteReservation(reservation.reservation.reservationId)
            }
        }
    }

    private fun showTimePicker(selectedDate: Date, onTimeSelected: (Long) -> Unit) {
        val selectedDateTime = LocalDateTime.ofInstant(selectedDate.toInstant(), ZoneOffset.UTC)

        val timePicker =
            MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).setHour(12)
                .setMinute(10).setTitleText("Select Appointment time").build()

        timePicker.addOnPositiveButtonClickListener {
            val hour = timePicker.hour
            val minute = timePicker.minute
            val dateTime = selectedDateTime.withHour(hour).withMinute(minute)
            val timestamp = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
            onTimeSelected(timestamp)
        }

        timePicker.show(childFragmentManager, "TIME_PICKER")
    }

    private fun showDatePicker(onDateAndTimeSelected: (Long) -> Unit) {
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDate = Date(selection)
            showTimePicker(selectedDate, onDateAndTimeSelected)
        }

        datePicker.show(childFragmentManager, "DATE_PICKER")
    }

    private fun showCreateReservationDialog() {
        val dialogCreateReservation = Dialog(requireContext())
        dialogCreateReservation.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogCreateReservation.setContentView(R.layout.dialog_add_update_reservation)

        val window = dialogCreateReservation.window ?: return
        window.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val btnSubmit = dialogCreateReservation.findViewById<Button>(R.id.btn_submit)
        val btnCancel = dialogCreateReservation.findViewById<Button>(R.id.btn_cancel)

        val spRoom = dialogCreateReservation.findViewById<Spinner>(R.id.sp_room)
        roomViewModel.roomsWithRoomType.observe(viewLifecycleOwner) { rooms ->
            val roomNames = rooms.map { RoomItem(it) }
            val adapter = ArrayAdapter(
                requireContext(), android.R.layout.simple_spinner_item, roomNames
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            spRoom.adapter = adapter
        }

        val spClient = dialogCreateReservation.findViewById<Spinner>(R.id.sp_client)
        clientViewModel.clients.observe(viewLifecycleOwner) { clients ->
            val clientNames = clients.map { ClientItem(it) }
            val adapter = ArrayAdapter(
                requireContext(), android.R.layout.simple_spinner_item, clientNames
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            spClient.adapter = adapter
        }

        val etDateTimeCheckIn =
            dialogCreateReservation.findViewById<TextInputEditText>(R.id.et_date_time_check_in)
        val etDateTimeCheckOut =
            dialogCreateReservation.findViewById<TextInputEditText>(R.id.et_date_time_check_out)
        var checkInTimestamp: Long = 0
        var checkOutTimestamp: Long = 0
        etDateTimeCheckIn.setOnClickListener {
            showDatePicker {
                checkInTimestamp = it
                etDateTimeCheckIn.setText(checkInTimestamp.convertTimestampToDateTime())
            }
        }
        etDateTimeCheckOut.setOnClickListener {
            showDatePicker {
                checkOutTimestamp = it
                etDateTimeCheckOut.setText(checkOutTimestamp.convertTimestampToDateTime())
            }
        }

        btnSubmit.setOnClickListener {
            val additionalExpenses =
                dialogCreateReservation.findViewById<TextInputEditText>(R.id.et_additional_expenses).text.toString()
            if (checkInTimestamp != 0L && checkOutTimestamp != 0L && additionalExpenses.isNotEmpty()) {
                val reservation = Reservation(
                    reservationId = 0,
                    checkInDate = checkInTimestamp,
                    checkOutDate = checkOutTimestamp,
                    clientId = (spClient.selectedItem as ClientItem).client.clientId,
                    roomId = (spRoom.selectedItem as RoomItem).roomWithRoomType.room.roomId,
                    additionalExpenses = additionalExpenses.toDouble()
                )
                reservationViewModel.insertReservation(reservation)
                Toast.makeText(requireContext(), "Reservation added", Toast.LENGTH_SHORT).show()
                dialogCreateReservation.dismiss()
            } else {
                Toast.makeText(
                    requireContext(), getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT
                ).show()
            }
        }
        btnCancel.setOnClickListener {
            dialogCreateReservation.dismiss()
        }
        dialogCreateReservation.show()
    }

    data class RoomItem(val roomWithRoomType: RoomWithRoomType) {
        override fun toString(): String {
            return roomWithRoomType.room.roomNumber.toString()
        }
    }

    data class ClientItem(val client: Client) {
        override fun toString(): String {
            return client.name
        }
    }
}