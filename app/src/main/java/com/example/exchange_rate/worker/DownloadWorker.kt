package com.example.exchange_rate.worker

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.exchange_rate.App
import com.example.exchange_rate.R
import com.example.exchange_rate.domain.GetRateUseCase
import com.example.exchange_rate.ui.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DownloadWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    companion object {
        const val NOTIFICATION_CHANNEL = "films_notification_channel"
        const val RATE = 70
    }
    @Inject
    lateinit var getRateUseCase: GetRateUseCase
    init { App.instance?.applicationComponent?.inject(this) }

    @SuppressLint("CheckResult")
    override fun doWork(): Result {

        getRateUseCase.getRate()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{

                if (it.valueRate > RATE){

                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                    val builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
                        .setSmallIcon(R.drawable.ic_baseline_attach_money_24)
                        .setContentTitle(applicationContext.getString(R.string.app_name))
                        .setContentText("The exchange rate has changed - " + it.valueRate.toString())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)

                    val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.notify(1, builder.build())
                }
            }

        return Result.success()
    }
}