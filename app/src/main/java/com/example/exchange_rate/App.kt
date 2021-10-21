package com.example.exchange_rate

import android.app.Application
import com.example.exchange_rate.di.ApplicationComponent
import com.example.exchange_rate.di.DaggerApplicationComponent

class App: Application() {
    companion object{
        var instance: App? = null
            private set
    }
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        applicationComponent = DaggerApplicationComponent
            .builder()
            .build()
    }
}