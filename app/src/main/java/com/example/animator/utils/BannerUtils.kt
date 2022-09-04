package com.example.animator.utils

import android.content.Context
import android.widget.Toast

object BannerUtils {

    fun showToast(message: String, context: Context) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_LONG
        ).show()
    }

}