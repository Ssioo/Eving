package com.whoissio.eving.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ItemAdsBinding
import com.whoissio.eving.models.Ads
import com.whoissio.eving.viewmodels.ContentsViewModel

class AdsRecyclerAdapter(viewModel: ContentsViewModel) :
    BaseRecyclerAdapter<Ads, ContentsViewModel, ItemAdsBinding>(
        viewModel,
        layoutId = R.layout.item_ads
    ) {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.HORIZONTAL, false)
    }


}