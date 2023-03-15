package com.example.mypractice.model.http.api

import com.example.mypractice.base.BaseData
import com.example.mypractice.model.bean.Article
import com.example.mypractice.model.bean.Banner
import retrofit2.http.GET
import retrofit2.http.Path

interface WanApi {
    @GET("banner/json")
    suspend fun getBanner(): BaseData<List<Banner>>

    //页码从0开始
    @GET("article/list/{page}/json")
    suspend fun getArticle(@Path("page") path: Int) : BaseData<Article>
}