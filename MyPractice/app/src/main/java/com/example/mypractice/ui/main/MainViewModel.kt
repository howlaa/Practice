package com.example.mypractice.ui.main

import com.example.mypractice.base.BaseViewModel
import com.example.mypractice.base.IUiIntent

class MainViewModel : BaseViewModel<MainState, MainIntent>() {

    override fun initUiState(): MainState {
        return MainState(BannerUiState.INIT, DetailUiState.INIT)
    }

    override fun handleIntent(intent: IUiIntent) {
        when (intent) {
            MainIntent.GetBanner -> {
                requestDataWithFlow(
                    showLoading = true,
                    request = {mWanRepo.requestWanData()},
                    successCallback = { data -> sendUiState { copy(bannerUiState = BannerUiState.SUCCESS(data)) }},
                    failCallback = {}
                )
            }
            is MainIntent.GetDetail -> {
                requestDataWithFlow(
                    showLoading = false,
                    request = { mWanRepo.requestRankData(intent.page)},
                    successCallback = { data -> sendUiState { copy(detailUiState = DetailUiState.SUCCESS(data)) }}
                )
            }
        }
    }
}