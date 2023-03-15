package com.example.mypractice.ui.main

import com.example.mypractice.base.IUiState
import com.example.mypractice.model.bean.Article
import com.example.mypractice.model.bean.Banner

data class MainState(val bannerUiState: BannerUiState, val detailUiState: DetailUiState) : IUiState

sealed class BannerUiState {
    object INIT : BannerUiState()
    data class SUCCESS(val models: List<Banner>) : BannerUiState()
}

sealed class DetailUiState {
    object INIT: DetailUiState()
    data class SUCCESS(val articles: Article) : DetailUiState()
}
