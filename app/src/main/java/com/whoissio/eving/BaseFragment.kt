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

abstract class BaseFragment<B: ViewDataBinding, VM: BaseViewModel, PVM: BaseViewModel>(@LayoutRes override val layoutId: Int)
    : Fragment(layoutId), BaseFragmentView<VM, PVM> {
    protected lateinit var vm: VM
    protected lateinit var pvm: PVM
    protected lateinit var binding: B

    protected var progressDialog: ProgressDialog? = null
    protected var messageDialog: SimpleMessageDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        vm = getViewModel()
        pvm = getParentViewModel()
        binding.setVariable(BR.vm, vm)
        binding.setVariable(BR.pvm, pvm)
        binding.lifecycleOwner = this

        /* Data Observing */
        vm.toastEvent.observe(viewLifecycleOwner, { it.get()?.let { showToast(getString(it)) }})
        vm.alertEvent.observe(viewLifecycleOwner, { it.get()?.let { showSimpleMessageDialog(getString(it)) }})
        vm.networkEvent.observe(viewLifecycleOwner, { onNetworkEventChanged(it) })

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

interface BaseFragmentView<VM : BaseViewModel, PVM: BaseViewModel> {
    @get:LayoutRes val layoutId : Int

    fun getViewModel() : VM

    fun getParentViewModel() : PVM

    fun onNetworkEventChanged(networkState: NetworkEvent.NetworkState?)

    fun initView(savedInstanceState: Bundle?)
}