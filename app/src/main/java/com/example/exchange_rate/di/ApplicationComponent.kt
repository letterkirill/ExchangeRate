package com.example.exchange_rate.di

import com.example.exchange_rate.ui.MainViewModel
import com.example.exchange_rate.worker.DownloadWorker
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RetrofitModule::class])
@Singleton
interface ApplicationComponent {
    fun inject(mainViewModel: MainViewModel)
    fun inject(downloadWorker: DownloadWorker)
}