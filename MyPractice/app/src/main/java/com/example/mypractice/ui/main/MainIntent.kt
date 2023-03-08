package com.example.mypractice.ui.main

import com.example.mypractice.base.IUiIntent

sealed class MainIntent : IUiIntent{
    object GetBanner : MainIntent()
    data class GetDetail(val page: Int) : MainIntent()
}