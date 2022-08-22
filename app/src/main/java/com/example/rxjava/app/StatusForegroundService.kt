package com.example.rxjava.app

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_HIGH
import com.example.a16_rxjava_domain.STATUS_FOREGROUND_ENGLISH_NAME_KEY
import com.example.a16_rxjava_domain.STATUS_FOREGROUND_KIND_KEY
import com.example.a16_rxjava_domain.STATUS_FOREGROUND_RUSSIAN_NAME_KEY
import com.example.rxjava.R

class StatusForegroundService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent) {
            null -> startForeground()
            else -> startForeground(intent)
        }

        return START_STICKY
    }


    private fun startForeground(intent: Intent? = null) {
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(
                getString(R.string.foreground_notification_channel_id),
                getString(R.string.foreground_notification_channel_name)
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val nameEnglish = intent?.getStringExtra(STATUS_FOREGROUND_ENGLISH_NAME_KEY)
            ?: getString(R.string.fragment_details_tvTitle_text)
        val nameRussian = intent?.getStringExtra(STATUS_FOREGROUND_RUSSIAN_NAME_KEY)
            ?: getString(R.string.fragment_details_tvTitleRussian_text)
        val kind = intent?.getStringExtra(STATUS_FOREGROUND_KIND_KEY)
            ?.replaceFirstChar { it.uppercase() }
            ?: getString(R.string.fragment_details_tvKind_text)


        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(PRIORITY_HIGH)
            .setContentText(getString(R.string.status_content_text, nameEnglish, nameRussian))
            .setContentTitle(kind)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        startForeground(101, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_NONE
        )

        with(channel) {
            lightColor = Color.BLUE
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        }

        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)

        return channelId
    }
}