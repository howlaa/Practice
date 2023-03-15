package com.example.mypractice.ui.main.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mypractice.base.BaseFragment
import com.example.mypractice.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater)
    }

    override fun initViews() {
    }

    override fun initDatas() {
    }
}