package com.whoissio.eving.adapters

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ItemContentsBinding
import com.whoissio.eving.models.Contents
import com.whoissio.eving.utils.Helpers.toPx
import com.whoissio.eving.viewmodels.ContentsViewModel
import com.whoissio.eving.views.MainActivity

class ContentsRecyclerAdapter(viewModel: ContentsViewModel)
    : BaseRecyclerAdapter<Contents, ContentsViewModel, ItemContentsBinding>(
    viewModel,
    R.layout.item_contents
) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Contents, ItemContentsBinding> = object : BaseViewHolder<Contents, ItemContentsBinding>(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
    ) {
        override fun initItem(item: Contents) {
            super.initItem(item)
            binding.root.setOnClickListener {
                try {
                    (binding.root.context as MainActivity).startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("vnd.youtube:" + item.video)
                        )
                    )
                } catch (e: Exception) {
                    (binding.root.context as MainActivity).startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                "http://www.youtube.com/watch?v=" + item.video
                            )
                        )
                    )
                }
            }
        }
    }

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