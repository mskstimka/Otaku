package com.example.otaku.utils

import android.content.Context
import android.widget.Toast

object BannerUtils {

    fun showToast(message: String, context: Context) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()

    }

}