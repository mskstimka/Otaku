package com.example.animator_data.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.*

class SharedPreferencesHelper(private val context: Context) {

    private val sharedPreference by lazy {
        context.getSharedPreferences(
            SHARED_PREF_SETTINGS,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    fun setIsCensoredSearch(switch: Boolean) {
        return sharedPreference.edit().putBoolean(IS_CENSORED_SEARCH, switch).apply()
    }

    fun getIsCensoredSearch(): Boolean {
        return sharedPreference.getBoolean(IS_CENSORED_SEARCH, true)
    }

    fun setDayNightTheme(themeId: Int) {
        when (themeId) {
            IS_DAY_THEME, IS_NIGHT_THEME, IS_AUTO_THEME -> sharedPreference.edit()
                .putInt(IS_DAY_NIGHT_THEME, themeId).apply()
        }
    }

    fun getDayNightTheme(): Int {
        return sharedPreference.getInt(IS_DAY_NIGHT_THEME, IS_AUTO_THEME)
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