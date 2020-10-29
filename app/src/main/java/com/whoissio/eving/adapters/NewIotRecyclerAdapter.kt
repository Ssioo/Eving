package com.whoissio.eving.adapters

import android.bluetooth.BluetoothDevice
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ItemAddableBleDeviceBinding
import com.whoissio.eving.viewmodels.IotViewModel

class NewIotRecyclerAdapter(viewmodel: IotViewModel) :
    BaseRecyclerAdapter<BluetoothDevice, IotViewModel, ItemAddableBleDeviceBinding>(
        viewmodel, layoutId = R.layout.item_addable_ble_device
    ) {

    fun addDevice(result: BluetoothDevice?) {
        if (!items.any { it?.address == result?.address }) {
            items.add(result)
            notifyDataSetChanged()
        }
    }

    override fun addItem(item: BluetoothDevice?) {
        if (!items.any { it?.address == item?.address }) {
            items.add(item)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        items.clear()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<BluetoothDevice, ItemAddableBleDeviceBinding> =
        object : BaseViewHolder<BluetoothDevice, ItemAddableBleDeviceBinding>(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
        ) {
            override fun initItem(item: BluetoothDevice) {
                super.initItem(item)
                binding.root.setOnClickListener {
                    Log.d("uuid", item.uuids?.toString() ?: "NULL")
                    MaterialAlertDialogBuilder(itemView.context).setMessage("디바이스를 연결할까요?")
                        .setPositiveButton(
                            "확인"
                        ) { dialog, which ->
                            viewmodel.registerIotDevice(item)
                            dialog.dismiss()
                        }.create().show()
                }
            }
        }
}