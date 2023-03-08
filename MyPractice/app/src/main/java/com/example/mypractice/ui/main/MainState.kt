package com.example.mypractice.ui.main

import com.example.mypractice.base.IUiState

data class MainState(val bannerUiState: BannerUiState, val detailUiState: DetailUiState) : IUiState

sealed class BannerUiState {
    object INIT : BannerUiState()
    data class SUCCESS(val models: List<BannerModel>) : BannerUiState()
}

sealed class DetailUiState {
    object INIT: DetailUiState()
    data class SUCCESS(val articles: ArticleModel) : DetailUiState()
}
