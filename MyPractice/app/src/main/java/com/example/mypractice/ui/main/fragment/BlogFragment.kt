package com.example.mypractice.ui.main.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mypractice.base.BaseFragment
import com.example.mypractice.databinding.FragmentBlogBinding

class BlogFragment : BaseFragment<FragmentBlogBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBlogBinding {
        return FragmentBlogBinding.inflate(inflater)
    }

    override fun initViews() {
    }

    override fun initDatas() {
    }
}