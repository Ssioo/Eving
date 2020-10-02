package com.whoissio.eving.adapters

import android.bluetooth.BluetoothDevice
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.whoissio.eving.R
import kotlinx.android.synthetic.main.item_addable_ble_device.view.*
import kotlin.collections.ArrayList

class NewIotRecyclerAdapter : RecyclerView.Adapter<NewIotRecyclerAdapter.LeViewHolder>() {

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
                Log.d("uuid", item.uuids?.toString() ?: "NULL")
                MaterialAlertDialogBuilder(itemView.context).setMessage("디바이스를 연결할까요?").setPositiveButton("확인"
                ) { dialog, which ->
                    dialog.dismiss()
                }.create().show()
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