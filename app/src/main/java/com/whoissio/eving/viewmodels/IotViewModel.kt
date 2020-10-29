package com.whoissio.eving.viewmodels

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.MutableLiveData
import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.R
import com.whoissio.eving.adapters.MyIotRecyclerAdapter
import com.whoissio.eving.adapters.NewIotRecyclerAdapter
import com.whoissio.eving.models.IotDevice
import com.whoissio.eving.models.IotType
import com.whoissio.eving.networks.services.IotService
import com.whoissio.eving.utils.Helpers.toDisposal
import com.whoissio.eving.utils.objects.SingleEvent

class IotViewModel: BaseViewModel() {
    val myIotAdapter = MyIotRecyclerAdapter(viewModel = this)
    val newIotRecyclerAdapter = NewIotRecyclerAdapter(viewmodel = this)

    val myIots: MutableLiveData<ArrayList<IotDevice?>> = MutableLiveData()

    fun tryFetchMyIotDevices() {
        networkEvent.startLoading()
        IotService().fetchMyIotDevices().toDisposal(rxDisposable, {
            networkEvent.handleResponse(it)
            it?.let {
                if (it.code != 200) {
                    return@let
                }
                it.data?.let { myIots.value = it }
            }
        }, {
            doOnNetworkError(it)
        })
    }

    fun registerIotDevice(device: BluetoothDevice) {
        if (myIots.value?.any { it?.address == device.address } == true) {
            alertEvent.value = SingleEvent(data = R.string.duplicate_device)
            return
        }
        networkEvent.startLoading()
        IotService().registerIotDevice(device.address, device.uuids?.get(0)?.uuid?.toString(), device.name, IotType.CUSTOM).toDisposal(rxDisposable, {
            networkEvent.handleResponse(it)
            it?.let {
                if (it.code != 200) {
                    alertEvent.value = SingleEvent(data = R.string.network_error)
                    return@let
                }
                tryFetchMyIotDevices()
            }
        }, { doOnNetworkError(it) })
    }

    fun deleteMyIotDevice(deviceId: Int) {
        networkEvent.startLoading()
        IotService().deleteMyIotDevice(deviceId).toDisposal(rxDisposable, {
            networkEvent.handleResponse(it)
            it?.let {
                if (it.code != 200) {
                    alertEvent.value = SingleEvent(data = R.string.network_error)
                    return@let
                }
            }
        }, { doOnNetworkError(it) })
    }

    init {
        tryFetchMyIotDevices()
    }
}