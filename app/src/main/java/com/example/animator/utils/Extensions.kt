package com.example.animator.utils

import android.widget.ImageView
import com.example.animator.R
import com.squareup.picasso.Picasso

fun ImageView.setImageByURL(url: String) {
    Picasso.get().load(url)
        .error(R.drawable.icon_default).into(this)
}

fun ImageView.setImageStudioByURL(url: String) {
    Picasso.get().load(url)
        .error(R.drawable.icon_studio_default).into(this)
}