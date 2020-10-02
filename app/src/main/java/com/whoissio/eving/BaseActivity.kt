package com.whoissio.eving

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.whoissio.eving.networks.NetworkEvent
import com.whoissio.eving.utils.SimpleMessageDialog

abstract class BaseActivity<B: ViewDataBinding, VM: BaseViewModel>: AppCompatActivity(), BaseActivityView<VM> {

    protected var binding : B? = null
    protected lateinit var viewmodel : VM

    protected var progressDialog: ProgressDialog? = null
    protected var messageDialog: SimpleMessageDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        viewmodel = getViewModel()
        binding?.setVariable(BR.vm, viewmodel)
        binding?.lifecycleOwner = this


        viewmodel.toastEvent.observe(this, { it.get()?.let { showToast(getString(it)) }})
        viewmodel.alertEvent.observe(this, { it.get()?.let { showSimpleMessageDialog(getString(it)) }})
        viewmodel.networkEvent.observe(this, { handleNetworkEvent(it) })

        initView(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

    open fun showToast(message: String?) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    open fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this, android.R.style.Theme_DeviceDefault_Dialog).apply {
                setMessage(getString(R.string.loading))
                isIndeterminate = true
                setCancelable(false)
            }
        }
        progressDialog!!.show()
    }

    open fun hideProgressDialog() = progressDialog?.apply { if (isShowing) dismiss() }

    open fun showSimpleMessageDialog(message: String?, btnText: String? = getString(R.string.confirm), isCancelable: Boolean = true, onClick : ((Dialog) -> Unit)? = null) {
        messageDialog = SimpleMessageDialog(this, message, btnText, isCancelable, onClick)
        messageDialog?.show()
    }

    override fun handleNetworkEvent(state: NetworkEvent.NetworkState?) {
        when(state) {
            NetworkEvent.NetworkState.LOADING -> showProgressDialog()
            NetworkEvent.NetworkState.SUCCESS -> {
                hideProgressDialog()
            }
            NetworkEvent.NetworkState.FAILURE -> {
                hideProgressDialog()
            }
            NetworkEvent.NetworkState.ERROR -> {
                hideProgressDialog()
                showToast(getString(R.string.network_error))
            }
            else -> {
                hideProgressDialog()
            }
        }
    }
}

interface BaseActivityView<VM : BaseViewModel> {
    @get:LayoutRes
    val layoutId: Int

    fun getViewModel() : VM

    fun initView(savedInstanceState: Bundle?)

    fun handleNetworkEvent(state : NetworkEvent.NetworkState?)
}