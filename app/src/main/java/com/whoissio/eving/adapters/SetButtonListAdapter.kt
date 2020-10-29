package com.whoissio.eving.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ItemSetButtonBinding
import com.whoissio.eving.utils.Helpers.toPx
import com.whoissio.eving.viewmodels.ExerciseRecordDetailViewModel

class SetButtonListAdapter(vm: ExerciseRecordDetailViewModel): BaseRecyclerAdapter<SetButtonListAdapter.SelectableIdx, ExerciseRecordDetailViewModel, ItemSetButtonBinding>(
    viewmodel = vm,
    layoutId = R.layout.item_set_button
) {

    data class SelectableIdx(
        var set: Int,
        var isSelected: Boolean,
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SelectableIdx, ItemSetButtonBinding> = object : BaseViewHolder<SelectableIdx, ItemSetButtonBinding>(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
    ) {
        override fun initItem(item: SelectableIdx) {
            super.initItem(item)
            binding.btnSelect.setOnClickListener {
                items.forEach { it?.isSelected = false }
                item.isSelected = true
                viewmodel.selectedSet.value = item.set
                notifyDataSetChanged()
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(MarginItemDecoration())
    }

    class MarginItemDecoration: RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.right = 10.toPx(parent.context)
        }
    }
}