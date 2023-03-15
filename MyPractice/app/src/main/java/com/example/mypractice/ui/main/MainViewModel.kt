package com.example.mypractice.ui.main

import com.example.mypractice.base.BaseViewModel
import com.example.mypractice.base.IUiIntent
import com.example.mypractice.model.repository.HomeRepository

class MainViewModel(private val homeRepo: HomeRepository) : BaseViewModel<MainState, MainIntent>() {

    override fun initUiState(): MainState {
        return MainState(BannerUiState.INIT, DetailUiState.INIT)
    }

    override fun handleIntent(intent: IUiIntent) {
        when (intent) {
            MainIntent.GetBanner -> {
                requestDataWithFlow(
                    showLoading = true,
                    request = {homeRepo.requestWanData()},
                    successCallback = { data -> sendUiState { copy(bannerUiState = BannerUiState.SUCCESS(data)) }},
                    failCallback = {}
                )
            }
            is MainIntent.GetDetail -> {
                requestDataWithFlow(
                    showLoading = false,
                    request = { homeRepo.requestRankData(intent.page)},
                    successCallback = { data -> sendUiState { copy(detailUiState = DetailUiState.SUCCESS(data)) }}
                )
            }
        }
    }
}