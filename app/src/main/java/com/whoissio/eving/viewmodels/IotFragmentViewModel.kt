package com.whoissio.eving.viewmodels

import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.adapters.MyIotRecyclerAdapter
import com.whoissio.eving.adapters.NewIotRecyclerAdapter

class IotFragmentViewModel: BaseViewModel() {
    val myIotAdapter = MyIotRecyclerAdapter()
    val newIotRecyclerAdapter = NewIotRecyclerAdapter()

}