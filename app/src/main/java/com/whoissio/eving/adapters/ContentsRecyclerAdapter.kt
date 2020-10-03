package com.whoissio.eving.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ItemContentsBinding
import com.whoissio.eving.models.Contents
import com.whoissio.eving.utils.Helpers.toPx
import com.whoissio.eving.viewmodels.ContentsViewModel

class ContentsRecyclerAdapter(
    override val layoutId: Int = R.layout.item_contents,
    viewModel: ContentsViewModel
) : BaseRecyclerAdapter<Contents, ContentsViewModel, ItemContentsBinding>(viewModel) {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(ContentsItemDecoration())
    }


    class ContentsItemDecoration: RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom = 20.toPx(parent.context)
        }
    }
}