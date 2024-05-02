package com.example.hotelmanagement.ui.client

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Filter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelmanagement.R
import com.example.hotelmanagement.data.model.Client
import com.example.hotelmanagement.databinding.FragmentClientBinding
import com.example.hotelmanagement.interfaces.IOnItemClickListener
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientFragment : Fragment(), IOnItemClickListener {
    private lateinit var binding: FragmentClientBinding
    private lateinit var clientAdapter: ClientAdapter
    private val viewModel: ClientViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentClientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.btnCreateClient.setOnClickListener {
            showCreateClientDialog()
        }
    }

    private fun setupRecyclerView() {
        clientAdapter = ClientAdapter(this)
        binding.rcvListClient.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvListClient.adapter = clientAdapter
    }

    private fun observeViewModel() {
        viewModel.clients.observe(viewLifecycleOwner) { clients ->
            clientAdapter.submitList(clients)
        }
    }

    private fun showCreateClientDialog() {
        val dialogCreateClient = Dialog(requireContext())
        dialogCreateClient.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogCreateClient.setContentView(R.layout.dialog_add_update_client)
        val window = dialogCreateClient.window ?: return
        window.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        fillMenuSexWithValue(dialogCreateClient, getString(R.string.sex))

        val btnSubmit = dialogCreateClient.findViewById<Button>(R.id.btn_submit)
        val btnCancel = dialogCreateClient.findViewById<Button>(R.id.btn_cancel)

        btnSubmit.setOnClickListener {
            val clientName =
                dialogCreateClient.findViewById<TextInputEditText>(R.id.et_client_name).text.toString()
            val clientPhone =
                dialogCreateClient.findViewById<TextInputEditText>(R.id.et_client_phone_number).text.toString()
            val clientCountry =
                dialogCreateClient.findViewById<TextInputEditText>(R.id.et_client_country).text.toString()
            val clientSex =
                dialogCreateClient.findViewById<AutoCompleteTextView>(R.id.actv_menu_sex).text.toString()

            if (clientName.isNotEmpty() && clientPhone.isNotEmpty() && clientCountry.isNotEmpty() && clientSex.isNotEmpty()) {
                val client = Client(0, clientName, clientSex, clientCountry, clientPhone)
                viewModel.insertClient(client)
                dialogCreateClient.dismiss()
            } else {
                Toast.makeText(
                    requireContext(), getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT
                ).show()
            }
        }
        btnCancel.setOnClickListener {
            dialogCreateClient.dismiss()
        }
        dialogCreateClient.show()
    }

    private fun showEditClientDialog(client: Client) {
        val dialogEditDialog = Dialog(requireContext())
        dialogEditDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogEditDialog.setContentView(R.layout.dialog_add_update_client)
        val window = dialogEditDialog.window ?: return
        window.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        fillMenuSexWithValue(dialogEditDialog, client.gender)

        val btnSubmit = dialogEditDialog.findViewById<Button>(R.id.btn_submit)
        val btnCancel = dialogEditDialog.findViewById<Button>(R.id.btn_cancel)

        dialogEditDialog.findViewById<TextInputEditText>(R.id.et_client_name).setText(client.name)
        dialogEditDialog.findViewById<TextInputEditText>(R.id.et_client_phone_number)
            .setText(client.phoneNumber)
        dialogEditDialog.findViewById<TextInputEditText>(R.id.et_client_country)
            .setText(client.country)

        btnSubmit.setOnClickListener {
            val clientName =
                dialogEditDialog.findViewById<TextInputEditText>(R.id.et_client_name).text.toString()
            val clientPhone =
                dialogEditDialog.findViewById<TextInputEditText>(R.id.et_client_phone_number).text.toString()
            val clientCountry =
                dialogEditDialog.findViewById<TextInputEditText>(R.id.et_client_country).text.toString()
            val clientSex =
                dialogEditDialog.findViewById<AutoCompleteTextView>(R.id.actv_menu_sex).text.toString()

            if (clientName.isNotEmpty() || clientPhone.isNotEmpty() || clientCountry.isNotEmpty() || clientSex.isNotEmpty()) {
                val newClient =
                    Client(client.clientId, clientName, clientSex, clientCountry, clientPhone)
                viewModel.updateClient(newClient)
                dialogEditDialog.dismiss()
            } else {
                Toast.makeText(
                    requireContext(), getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT
                ).show()
            }
        }
        btnCancel.setOnClickListener {
            dialogEditDialog.dismiss()
        }
        dialogEditDialog.show()
    }

    private fun fillMenuSexWithValue(dialog: Dialog, sex: String) {
        val sexes = listOf("Male", "Female", "Other")
        val adapter =
            object : ArrayAdapter<String>(requireContext(), R.layout.item_list_menu, sexes) {
                override fun getFilter(): Filter {
                    return object : Filter() {
                        override fun performFiltering(constraint: CharSequence?): FilterResults {
                            return FilterResults().apply {
                                values = sexes; count = sexes.size
                            }
                        }

                        override fun publishResults(
                            constraint: CharSequence?, results: FilterResults?
                        ) {
                            notifyDataSetChanged()
                        }
                    }
                }
            }
        val actvMenuSex = dialog.findViewById<AutoCompleteTextView>(R.id.actv_menu_sex)
        actvMenuSex.setText(sex)
        actvMenuSex.setAdapter(adapter)
    }

    override fun <T> onItemClick(item: T, isLongClick: Boolean, view: View) {
        if (item is Client) {
            if (isLongClick) {
                val popupMenu = PopupMenu(requireContext(), view)
                popupMenu.menuInflater.inflate(R.menu.menu_popup, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.action_delete -> {
                            viewModel.deleteClient(item)
                            true
                        }

                        R.id.action_edit -> {
                            showEditClientDialog(item)
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