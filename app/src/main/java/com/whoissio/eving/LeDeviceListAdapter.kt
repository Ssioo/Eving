package com.whoissio.eving

import android.bluetooth.BluetoothDevice
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.item_addable_ble_device.view.*

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
            itemView.text_device_name_ble.text = item.name ?: item.address
            itemView.btn_connect_ble.setOnClickListener {
                MaterialAlertDialogBuilder(itemView.context).setMessage("디바이스를 연결할까요?").setPositiveButton("확인",
                    { dialog, which ->
                        dialog.dismiss()
                    }).create().show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeViewHolder =
        LeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_addable_ble_device, parent, false))

    override fun onBindViewHolder(holder: LeViewHolder, position: Int) {
        items.get(position)?.let {
            holder.bindTo(it)
        }
    }

    override fun getItemCount(): Int = items.size
}