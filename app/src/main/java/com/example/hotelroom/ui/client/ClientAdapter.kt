package com.example.hotelroom.ui.client

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelroom.R
import com.example.hotelroom.database.model.Client
import com.example.hotelroom.databinding.ItemClientBinding
import com.example.hotelroom.interfaces.IOnItemClickListener

class ClientAdapter(
    private val listener: IOnItemClickListener
) : RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {

    private var clients: List<Client> = emptyList()

    class ClientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemClientBinding = ItemClientBinding.bind(view)
        val clientName = binding.tvName
        val clientSex = binding.imgClientSex
        val clientPhone = binding.tvPhoneNumber
        val clientCountry = binding.tvCountry
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_client, parent, false)
        return ClientViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        holder.clientName.text = clients[position].name
        holder.clientCountry.text = clients[position].country
        holder.clientPhone.text = clients[position].phoneNumber
        when (clients[position].gender) {
            getString(holder.clientSex.context, R.string.male) -> {
                holder.clientSex.setImageDrawable(
                    getDrawable(
                        holder.clientSex.context, R.drawable.ic_male
                    )
                )
            }

            getString(
                holder.clientSex.context, R.string.female
            ) -> {
                holder.clientSex.setImageDrawable(
                    getDrawable(
                        holder.clientSex.context, R.drawable.ic_female
                    )
                )
            }

            else -> {
                holder.clientSex.setImageDrawable(
                    getDrawable(
                        holder.clientSex.context, R.drawable.ic_unknown_sex
                    )
                )
            }
        }
        holder.itemView.setOnClickListener {
            listener.onItemClick(clients[position], false, it)
        }
        holder.itemView.setOnLongClickListener {
            listener.onItemClick(clients[position], true, it)
            true
        }
    }

    override fun getItemCount(): Int = clients.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newClients: List<Client>) {
        this.clients = newClients
        notifyDataSetChanged()
    }
}