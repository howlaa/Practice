package com.example.mypractice

import android.app.Application
import com.example.mypractice.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            //开始启动Koin
            androidLogger()
            //这边传Application对象，这样你注入的类中，需要app对象的时候，可以直接使用
            androidContext(this@App)
            //这里面传各种被注入的模块对象，支持多模块注入
            modules(appModule)
        }
    }
}