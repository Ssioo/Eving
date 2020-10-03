package com.whoissio.eving.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.whoissio.eving.BR

abstract class BaseViewHolder<I, B : ViewDataBinding>(var binding : B)
    : RecyclerView.ViewHolder(binding.root),
    BaseBindingViewHolder<I> {

    open fun bindTo(item: I) {
        binding.setVariable(BR.item, item)
        initItem(item)
    }

    override fun initItem(item: I) {}
}

interface BaseBindingViewHolder<I> {
    fun initItem(item: I)
}