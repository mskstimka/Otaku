package com.example.otaku.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.widget.ImageView
import androidx.lifecycle.*
import com.example.otaku.R
import com.example.otaku_domain.NOT_FOUND_TEXT
import com.example.otaku_domain.models.details.AnimeDetailsEntity
import com.example.otaku_domain.models.poster.AnimePosterEntity
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

fun Context.copyToClipboard(text: CharSequence) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
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

fun <T> Flow<T>.subscribeToFlow(
    lifecycleOwner: LifecycleOwner,
    active: (item: T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@subscribeToFlow.collect {
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

fun AnimeDetailsEntity.toAnimePoster() = AnimePosterEntity(
    id = this.id,
    image = this.image,
    name = this.name ?: NOT_FOUND_TEXT,
    score = this.score ?: "0.0",
    episodes = this.episodes ?: 0,
    episodesAired = this.episodes_aired ?: 0,
    url = NOT_FOUND_TEXT,
    status = this.status ?: NOT_FOUND_TEXT,
    statusColor = "",
    russian = this.russian ?: ""
)