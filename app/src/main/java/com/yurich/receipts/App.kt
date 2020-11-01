package com.yurich.receipts

import android.app.Application
import com.yurich.receipts.di.dbModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        startKoin {
            androidContext(this@App)
            modules(
                dbModule
            )
        }
    }
}
