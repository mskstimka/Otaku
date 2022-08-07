package com.example.rxjava.app.utils

import android.content.Context
import android.widget.Toast

object BannerUtils {

    fun showToastError(message: String, context: Context) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}