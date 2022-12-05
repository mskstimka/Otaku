package com.example.otaku.utils

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.*
import com.example.otaku.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

fun ImageView.setImageByURL(url: String) {
    Picasso.get().load(url)
        .error(R.drawable.icon_default).into(this)
}

fun ImageView.setImageStudioByURL(url: String) {
    Picasso.get().load(url)
        .error(R.drawable.icon_studio_default).into(this)
}



fun <T> ViewModel.subscribeToFlow(
    flow: SharedFlow<T>,
    lifecycleOwner: LifecycleOwner,
    active: (item: T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect() {
                active(it)
            }

        }
    }
}

fun Context.applyNewLocale(locale: Locale): Context {
    val config = this.resources.configuration
    val sysLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        config.locales.get(0)
    } else {
        //Legacy
        config.locale
    }
    if (sysLocale.language != locale.language) {
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
        } else {
            //Legacy
            config.locale = locale
        }
        resources.updateConfiguration(config, resources.displayMetrics)
    }
    return this
}