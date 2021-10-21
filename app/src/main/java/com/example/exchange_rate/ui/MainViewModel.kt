package com.example.exchange_rate.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exchange_rate.App
import com.example.exchange_rate.data.RateData
import com.example.exchange_rate.domain.GetRateUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel: ViewModel() {
    private val rateLiveData = MutableLiveData<List<RateData>>()
    val rateList: LiveData<List<RateData>> get() = rateLiveData

    @Inject
    lateinit var getRateUseCase: GetRateUseCase

    init { App.instance?.applicationComponent?.inject(this) }

    @SuppressLint("CheckResult")
    fun getMonthRates() {

        getRateUseCase.getMonthRate()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { listRate ->
                rateLiveData.postValue(listRate)
            }
    }
}
