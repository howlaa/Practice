package com.example.mypractice.model.repository

import com.example.mypractice.base.BaseData
import com.example.mypractice.base.BaseRepository
import com.example.mypractice.model.bean.Article
import com.example.mypractice.model.bean.Banner
import com.example.mypractice.model.http.WanRetrofitClient
import com.example.mypractice.model.http.api.WanApi

class HomeRepository : BaseRepository() {
    private val service = WanRetrofitClient.getService(WanApi::class.java)

    suspend fun requestWanData(): BaseData<List<Banner>> {
        return executeRequest { service.getBanner() }
    }

    suspend fun requestRankData(page: Int): BaseData<Article> {
        return executeRequest { service.getArticle(page) }
    }


}