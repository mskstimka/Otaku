package com.example.otaku.utils

import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.otaku.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun ImageView.setImageByURL(url: String) {
    Picasso.get().load(url)
        .placeholder(R.color.placeholder_color)
        .error(R.drawable.icon_default).into(this)
}

fun ImageView.setImageStudioByURL(url: String) {
    Picasso.get().load(url)
        .error(R.drawable.icon_studio_default).into(this)
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