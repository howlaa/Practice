package com.example.mypractice.ui.main.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mypractice.base.BaseFragment
import com.example.mypractice.databinding.FragmentProjectBinding

class ProjectFragment : BaseFragment<FragmentProjectBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectBinding {
        return FragmentProjectBinding.inflate(inflater)
    }

    override fun initViews() {
    }

    override fun initDatas() {
    }
}