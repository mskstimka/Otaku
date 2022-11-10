package com.example.otaku.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.animator_domain.*

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

    fun setIsUkraineLanguage(switch: Boolean) {
        sharedPreference.edit().putBoolean(IS_UK_LANGUAGE, switch).apply()
    }

    fun getIsUkraineLanguage(): Boolean {
        return sharedPreference.getBoolean(IS_UK_LANGUAGE, false)
    }

    fun setIsUkraineTitle(switch: Boolean) {
        sharedPreference.edit().putBoolean(IS_TITLE_UKRAINE, switch).apply()
    }

    fun getIsUkraineTitle(): Boolean {
        return sharedPreference.getBoolean(IS_TITLE_UKRAINE, true)
    }

    fun setIsUkraineName(switch: Boolean) {
        sharedPreference.edit().putBoolean(IS_NAME_UKRAINE, switch).apply()
    }

    fun getIsUkraineName(): Boolean {
        return sharedPreference.getBoolean(IS_NAME_UKRAINE, true)
    }

    fun setIsUkraineDescription(switch: Boolean) {
        sharedPreference.edit().putBoolean(IS_DESCRIPTION_UKRAINE, switch).apply()
    }

    fun getIsUkraineDescription(): Boolean {
        return sharedPreference.getBoolean(IS_DESCRIPTION_UKRAINE, true)
    }
}