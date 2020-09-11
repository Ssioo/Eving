package com.whoissio.eving

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_le.view.*

class LeDeviceListAdapter : RecyclerView.Adapter<LeDeviceListAdapter.LeViewHolder>() {

    private val items: ArrayList<BluetoothDevice?> = ArrayList()

    fun addDevice(result: BluetoothDevice?) {
        if (!items.any { it?.address == result?.address }) {
            items.add(result)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        items.clear()
    }

    class LeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTo(item: BluetoothDevice) {
            itemView.findViewById<TextView>(R.id.text_le).text = item.address
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeViewHolder =
        LeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_le, parent, false))

    override fun onBindViewHolder(holder: LeViewHolder, position: Int) {
        items.get(position)?.let {
            holder.bindTo(it)
        }
    }

    override fun getItemCount(): Int = items.size
}