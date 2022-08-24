package com.example.animator.app.firebase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.animator_domain.IS_SHOW_NOTIFICATION
import com.example.animator_domain.SHARED_PREF_SETTINGS
import com.google.firebase.messaging.FirebaseMessagingService


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FirebaseMessagingServiceImpl : FirebaseMessagingService() {

    override fun handleIntent(intent: Intent?) {
        val sharedPreferences = getSharedPreferences(
            SHARED_PREF_SETTINGS,
            AppCompatActivity.MODE_PRIVATE
        )
        val isShowNotification = sharedPreferences.getBoolean(IS_SHOW_NOTIFICATION, false)

        if (isShowNotification) {
            super.handleIntent(intent)
        }

    }

}
