package com.example.exchange_rate.data.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("XML_daily.asp")
    fun getRate(@Query("date_req") date: String): Observable<ValutaRates>
}