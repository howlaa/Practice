package com.example.mypractice.ui.main.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mypractice.base.BaseFragment
import com.example.mypractice.databinding.FragmentMeBinding

class MeFragment : BaseFragment<FragmentMeBinding>(){
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeBinding {
        return FragmentMeBinding.inflate(inflater)
    }

    override fun initViews() {
    }

    override fun initDatas() {
    }
}