package com.whoissio.eving.views

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.whoissio.eving.BaseFragment
import com.whoissio.eving.R
import com.whoissio.eving.databinding.FragmentContentsBinding
import com.whoissio.eving.viewmodels.ContentsViewModel

class ContentsFragment : BaseFragment<FragmentContentsBinding, ContentsViewModel>(layoutId = R.layout.fragment_contents) {
    override fun getViewModel(): ContentsViewModel =
        ViewModelProvider(this).get(ContentsViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        binding?.rvContents?.adapter = viewmodel.contentsRecyclerAdapter
    }
}