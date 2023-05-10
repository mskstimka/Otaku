package com.example.otaku.utils

import android.content.Context
import android.content.ContextWrapper
import android.os.LocaleList
import java.util.*

class ContextUtils(base: Context) : ContextWrapper(base) {
    companion object {
        fun updateLocale(context: Context, localeToSwitchTo: Locale): ContextUtils {
            val resources = context.resources
            val configuration = resources.configuration // 1
            val localeList = LocaleList(localeToSwitchTo) // 2
            LocaleList.setDefault(localeList) // 3
            configuration.setLocales(localeList) // 4
            val updatedContext = context.createConfigurationContext(configuration) // 5
            return ContextUtils(updatedContext)
        }
    }
}