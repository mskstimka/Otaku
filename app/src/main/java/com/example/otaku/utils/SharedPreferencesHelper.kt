package com.example.otaku.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.animator_domain.IS_SHOW_NOTIFICATION
import com.example.animator_domain.IS_UK_LANGUAGE
import com.example.animator_domain.SHARED_PREF_SETTINGS

class SharedPreferencesHelper(context: Context) {

    private val sharedPreference by lazy {
        context.getSharedPreferences(
            SHARED_PREF_SETTINGS,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    fun setIsShowNotification(switch: Boolean) {
        sharedPreference.edit().putBoolean(IS_SHOW_NOTIFICATION, switch).apply()
    }

    fun getIsShowNotification(): Boolean {
        return sharedPreference.getBoolean(IS_SHOW_NOTIFICATION, true)
    }

    fun setIsUkraineLanguage(switch: Boolean){
        sharedPreference.edit().putBoolean(IS_UK_LANGUAGE, switch).apply()
    }

    fun getIsUkraineLanguage(): Boolean {
        return sharedPreference.getBoolean(IS_UK_LANGUAGE, false)
    }
}