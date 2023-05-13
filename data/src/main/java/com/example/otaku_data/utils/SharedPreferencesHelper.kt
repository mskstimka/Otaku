package com.example.otaku_data.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.otaku_domain.*
import com.example.otaku_domain.models.Token
import com.google.gson.Gson
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(private val context: Context) {

    private val sharedPreference by lazy {
        context.getSharedPreferences(
            SHARED_PREF_SETTINGS,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    fun setCurrentUserId(newId: Long) {
        sharedPreference.edit().putLong(CURRENT_USER_ID, newId).apply()
    }

    fun getCurrentUserId(): Long {
        return sharedPreference.getLong(CURRENT_USER_ID, NO_AUTH_USER_ID)
    }

    fun removeLocalToken() {
        sharedPreference.edit().remove(LOCAL_TOKEN_KEY).apply()
    }

    fun setLocalToken(token: Token) {
        val jsonToken = Gson().toJson(token)
        sharedPreference.edit().putString(LOCAL_TOKEN_KEY, jsonToken).apply()
    }

    fun getLocalToken(): Token? {
        val jsonToken = sharedPreference.getString(LOCAL_TOKEN_KEY, null)
        return Gson().fromJson(jsonToken, Token::class.java)
    }

    fun setIsShowUserAgreement(switch: Boolean) {
        sharedPreference.edit().putBoolean(IS_SHOW_USER_AGREEMENT, switch).apply()
    }

    fun getIsShowUserAgreement(): Boolean {
        return sharedPreference.getBoolean(IS_SHOW_USER_AGREEMENT, true)
    }

    fun setIsCensoredSearch(switch: Boolean) {
        sharedPreference.edit().putBoolean(IS_CENSORED_SEARCH, switch).apply()
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