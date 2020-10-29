package com.whoissio.eving.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imgUrl")
    fun bindImgUrl(view: ImageView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .thumbnail(0.1f)
            .into(view)
        view.clipToOutline = true
    }
}