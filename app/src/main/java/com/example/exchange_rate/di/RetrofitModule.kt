package com.example.exchange_rate.di

import com.example.exchange_rate.data.network.RetrofitService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {
    companion object{
        const val BASE_URL = "http://cbr.ru/scripts/"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(okHttpClient: OkHttpClient): RetrofitService{
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }
}