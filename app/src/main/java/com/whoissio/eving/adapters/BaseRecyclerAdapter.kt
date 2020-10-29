package com.whoissio.eving.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.whoissio.eving.BaseViewModel

abstract class BaseRecyclerAdapter<I, VM : BaseViewModel, B : ViewDataBinding>(
    protected var viewmodel: VM,
    @LayoutRes override val layoutId: Int
) :
    RecyclerView.Adapter<BaseViewHolder<I, B>>(),
    BaseRecyclerAdapterInterface<I> {

    val items: ArrayList<I?> = ArrayList()

    override fun setItems(items: ArrayList<I?>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun addItems(items: ArrayList<I?>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun addItem(item: I?) {
        this.items.add(item)
        notifyItemChanged(items.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<I, B> =
        object : BaseViewHolder<I, B>(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
        ) {}

    override fun onBindViewHolder(holder: BaseViewHolder<I, B>, position: Int) {
        items.get(position)?.let { holder.bindTo(it) }
    }

    override fun getItemCount(): Int = items.size

}

interface BaseRecyclerAdapterInterface<I> {
    @get:LayoutRes
    val layoutId: Int

    fun setItems(items: ArrayList<I?>)

    fun addItems(items: ArrayList<I?>)

    fun addItem(item: I?)
}