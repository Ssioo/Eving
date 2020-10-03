package com.whoissio.eving

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.whoissio.eving.networks.NetworkEvent
import com.whoissio.eving.utils.components.SimpleMessageDialog

abstract class BaseFragment<B: ViewDataBinding, VM: BaseViewModel>(@LayoutRes override val layoutId: Int): Fragment(layoutId), BaseFragmentView<VM> {

    protected lateinit var viewmodel: VM
    protected var binding: B? = null

    protected var progressDialog: ProgressDialog? = null
    protected var messageDialog: SimpleMessageDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)
        viewmodel = getViewModel()
        binding?.setVariable(BR.vm, viewmodel)
        binding?.lifecycleOwner = this

        /* Data Observing */
        viewmodel.toastEvent.observe(viewLifecycleOwner, { it.get()?.let { showToast(getString(it)) }})
        viewmodel.alertEvent.observe(viewLifecycleOwner, { it.get()?.let { showSimpleMessageDialog(getString(it)) }})
        viewmodel.networkEvent.observe(viewLifecycleOwner, { onNetworkEventChanged(it) })

        initView(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

    override fun onNetworkEventChanged(networkState: NetworkEvent.NetworkState?) {
        networkState?.let {
            when (it) {
                NetworkEvent.NetworkState.LOADING -> showProgressDialog()
                NetworkEvent.NetworkState.ERROR -> {
                    hideProgressDialog()
                    showToast(getString(R.string.network_error))
                }
                NetworkEvent.NetworkState.FAILURE, NetworkEvent.NetworkState.SUCCESS -> hideProgressDialog()
            }
        } ?: hideProgressDialog()
    }

    open fun showToast(message: String?) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    open fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(context, android.R.style.Theme_DeviceDefault_Dialog)
            progressDialog!!.setMessage(getString(R.string.loading))
            progressDialog!!.isIndeterminate = true
            progressDialog!!.setCancelable(false)
        }
        progressDialog!!.show()
    }

    open fun hideProgressDialog() = progressDialog?.let { if (it.isShowing) it.dismiss() }

    open fun showSimpleMessageDialog(message: String?, btnText: String? = getString(R.string.confirm), isCancelable: Boolean = true, onClick : ((Dialog) -> Unit)? = null) {
        messageDialog = SimpleMessageDialog(requireContext(), message, btnText, isCancelable, onClick)
        messageDialog?.show()
    }
}

interface BaseFragmentView<VM : BaseViewModel> {
    @get:LayoutRes val layoutId : Int

    fun getViewModel() : VM

    fun onNetworkEventChanged(networkState: NetworkEvent.NetworkState?)

    fun initView(savedInstanceState: Bundle?)
}