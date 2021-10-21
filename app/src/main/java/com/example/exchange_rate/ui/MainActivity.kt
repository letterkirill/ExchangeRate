package com.example.exchange_rate.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.exchange_rate.R
import com.example.exchange_rate.worker.DownloadWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    companion object{
        const val WORKER = "WORKER"
    }
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = RateAdapter(LayoutInflater.from(applicationContext))

        val recyclerView = findViewById<RecyclerView>(R.id.rv_rate)
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 1)
        recyclerView.adapter = adapter
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)

        viewModel.rateList.observe(this, { rateList ->
            adapter.setList(rateList)
        })
        viewModel.getMonthRates()

        val work = PeriodicWorkRequestBuilder<DownloadWorker>(1, TimeUnit.DAYS).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(WORKER, ExistingPeriodicWorkPolicy.KEEP, work)
    }
}