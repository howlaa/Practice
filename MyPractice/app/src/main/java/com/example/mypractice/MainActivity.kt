package com.example.mypractice

import android.os.Bundle
import androidx.activity.viewModels
import com.example.mypractice.base.BaseActivity
import com.example.mypractice.databinding.ActivityMainBinding
import androidx.lifecycle.lifecycleScope
import com.example.mypractice.ui.main.BannerUiState
import com.example.mypractice.ui.main.DetailUiState
import com.example.mypractice.ui.main.MainIntent
import com.example.mypractice.ui.main.MainViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val mViewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerEvent()
        binding.btnTest1.setOnClickListener {
            mViewModel.sendUiIntent(MainIntent.GetBanner)
            mViewModel.sendUiIntent(MainIntent.GetDetail(0))
        }
    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    private fun registerEvent() {
        lifecycleScope.launchWhenStarted {
            mViewModel.uiStateFlow.map {
                it.bannerUiState
            }.distinctUntilChanged().collect { bannerUiState ->
                when (bannerUiState) {
                    is BannerUiState.INIT -> {}
                    is BannerUiState.SUCCESS -> {
                        bannerAdapter.setList(bannerUiState.models)
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            mViewModel.uiStateFlow.map {
                it.detailUiState
            }.distinctUntilChanged().collect { detailUiSate ->
                when (detailUiSate) {
                    is DetailUiState.INIT -> {}
                    is DetailUiState.SUCCESS -> {
                        articleAdapter.setList(detailUiSate.articles.datas)
                    }
                }
            }
        }
    }


}