package com.whoissio.eving.views

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.whoissio.eving.BaseFragment
import com.whoissio.eving.R
import com.whoissio.eving.databinding.FragmentContentsBinding
import com.whoissio.eving.viewmodels.ContentsViewModel
import com.whoissio.eving.viewmodels.MainViewModel

class ContentsFragment : BaseFragment<FragmentContentsBinding, ContentsViewModel, MainViewModel>(layoutId = R.layout.fragment_contents) {

    override fun getViewModel(): ContentsViewModel =
        ViewModelProvider(this).get(ContentsViewModel::class.java)

    override fun getParentViewModel(): MainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        binding.vpAds.adapter = vm.adsRecyclerAdapter
        binding.diAds.setViewPager2(binding.vpAds)

        binding.rvContents.adapter = vm.contentsRecyclerAdapter

        vm.contents.observe(this) { vm.contentsRecyclerAdapter.setItems(it) }
        vm.ads.observe(this) { vm.adsRecyclerAdapter.setItems(it) }
        vm.curAdsIdx.observe(this) { binding.vpAds.setCurrentItem(it, true)}

        vm.registerAdsLoop()
    }
}