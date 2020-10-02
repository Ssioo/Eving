package com.whoissio.eving.viewmodels

import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.util.Log
import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.adapters.MyIotRecyclerAdapter
import com.whoissio.eving.views.NewIotRecyclerAdapter

class IotFragmentViewModel: BaseViewModel() {
    val myIotAdapter = MyIotRecyclerAdapter()
    val newIotRecyclerAdapter = NewIotRecyclerAdapter()

}