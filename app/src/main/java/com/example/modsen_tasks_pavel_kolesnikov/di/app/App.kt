package com.example.modsen_tasks_pavel_kolesnikov.di.app

import android.app.Application
import com.example.modsen_tasks_pavel_kolesnikov.di.appModule
import com.example.modsen_tasks_pavel_kolesnikov.di.dataModule
import com.example.modsen_tasks_pavel_kolesnikov.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}