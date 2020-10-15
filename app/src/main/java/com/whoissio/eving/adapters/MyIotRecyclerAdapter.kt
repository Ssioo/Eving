package com.whoissio.eving.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ItemAddedBleDeviceBinding
import com.whoissio.eving.models.IotDevice
import com.whoissio.eving.viewmodels.IotViewModel

class MyIotRecyclerAdapter(viewModel: IotViewModel) :
    BaseRecyclerAdapter<IotDevice, IotViewModel, ItemAddedBleDeviceBinding>(
        viewModel,
        R.layout.item_added_ble_device
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<IotDevice, ItemAddedBleDeviceBinding> =
        object : BaseViewHolder<IotDevice, ItemAddedBleDeviceBinding>(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_added_ble_device, parent, false
            )
        ) {
            override fun initItem(item: IotDevice) {
                super.initItem(item)
                binding.tvBleName.setOnClickListener {
                    MaterialAlertDialogBuilder(binding.root.context).setMessage("디바이스를 삭제할까요?")
                        .setPositiveButton("네") { dialog, _ ->
                            viewmodel.deleteMyIotDevice(item.id)
                            dialog.dismiss()
                        }.setNegativeButton("아니오") { dialog, _ -> dialog.dismiss() }

                }
            }
        }
}