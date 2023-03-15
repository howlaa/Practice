package com.example.mypractice.base

open class BaseRepository {
    suspend fun <T : Any> executeRequest(block: suspend () -> BaseData<T>): BaseData<T> {
        val baseData = block.invoke()
        if (baseData.code == 0) {
            //正确
            baseData.state = ReqState.Success
        } else {
            baseData.state = ReqState.Error
        }
        return baseData
    }
}