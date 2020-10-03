package com.whoissio.eving.adapters

import com.whoissio.eving.R
import com.whoissio.eving.databinding.ItemAddedBleDeviceBinding
import com.whoissio.eving.models.IotDevice
import com.whoissio.eving.viewmodels.IotViewModel

class MyIotRecyclerAdapter(override val layoutId: Int = R.layout.item_added_ble_device, viewModel: IotViewModel) :
    BaseRecyclerAdapter<IotDevice, IotViewModel, ItemAddedBleDeviceBinding>(viewModel) {


}