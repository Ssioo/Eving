package com.whoissio.eving.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.whoissio.eving.R
import com.whoissio.eving.models.IotDevice
import kotlinx.android.synthetic.main.item_added_ble_device.view.*

class MyIotRecyclerAdapter :
    RecyclerView.Adapter<MyIotRecyclerAdapter.AddedLeViewHolder>() {

    private val items: ArrayList<IotDevice?> = ArrayList()

    fun setItems(items: ArrayList<IotDevice?>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddedLeViewHolder =
        AddedLeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_added_ble_device ,parent, false))

    override fun onBindViewHolder(holder: AddedLeViewHolder, position: Int) {
        items.get(position)?.let {
            holder.bindTo(it)
        }
    }

    override fun getItemCount(): Int = items.size

    class AddedLeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTo(item: IotDevice) {
            itemView.tv_ble_name.text = item.name ?: item.address
        }
    }
}