package com.example.exchange_rate.domain

import android.annotation.SuppressLint
import com.example.exchange_rate.data.RateData
import com.example.exchange_rate.data.network.RetrofitService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetRateUseCase @Inject constructor(private val retrofitService: RetrofitService) {
    private val idUSD = "R01235"
    @SuppressLint("SimpleDateFormat")
    private val formatter = SimpleDateFormat("dd/MM/yyyy")

    @SuppressLint("CheckResult")
    fun getRate(): Observable<RateData>{

        val calendar = Calendar.getInstance()

        return retrofitService.getRate(formatter.format(calendar.time))
            .subscribeOn(Schedulers.io())
            .map { RateData(calendar.time, it.toValue(idUSD)) }
    }

    @SuppressLint("CheckResult", "NewApi")
    fun getMonthRate(): Observable<List<RateData>> {

        val calendar = Calendar.getInstance()
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE))

        val dateTo = calendar.time
        calendar.add(Calendar.MONTH, - 1)
        val dateFrom = calendar.time

        val datesList = mutableListOf<Date>()

        var currentDate = dateFrom
        while (currentDate <= dateTo){
            datesList.add(currentDate)
            calendar.add(Calendar.DATE, 1)
            currentDate = calendar.time
        }

        return Observable.fromIterable(datesList)
            .subscribeOn(Schedulers.io())
            .flatMap{ date ->
                retrofitService.getRate(formatter.format(date))
                    .subscribeOn(Schedulers.io())
                    .map { RateData(date, it.toValue(idUSD)) }
            }
            .toList()
            .map{ rateList ->
                val list =rateList.sortedBy { it.date.time }
                list.reversed()
            }
            .toObservable()
    }
}