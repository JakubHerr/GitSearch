package com.example.gitsearch

import android.app.Application
import com.example.gitsearch.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GitSearchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GitSearchApplication)
            modules(AppModule)
        }
    }
}