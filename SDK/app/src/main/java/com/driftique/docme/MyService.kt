package com.driftique.docme

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log


class MyService(): android.app.Service() {
    val LOG_TAG = "myLogs"

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "onCreate")
        startAsForeground()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy")
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(LOG_TAG, "onBind")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LOG_TAG, "onStartCommand")
        ServiceWork()
        return START_STICKY
    }

    fun ServiceWork() {
        val t = HandlerThread("thread_for_service")
        t.start()
        Handler(t.looper).post {
            for (i in 1..100) {
                Log.d(LOG_TAG, i.toString())
                Thread.sleep(5000)
            }
        }
    }

    private fun startAsForeground() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val notification = NotificationCompat
                .Builder(this, "rtpDisplayStreamChannel")
                .setOngoing(true)
                .setContentTitle("")
                .setContentText("").build()
            startForeground(1, notification)
        } else {
            startForeground(1, Notification())
        }
    }
}