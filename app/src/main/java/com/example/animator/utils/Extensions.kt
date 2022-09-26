package com.example.animator.utils

import android.widget.ImageView
import androidx.lifecycle.*
import com.example.animator.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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
