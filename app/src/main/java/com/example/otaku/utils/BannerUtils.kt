package com.example.otaku.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.otaku.R
import com.example.otaku.app.activities.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

object BannerUtils {

    fun showSnackBar(
        view: View,
        message: String,
        context: Context,
        bottomNavigationView: BottomNavigationView? = null
    ) {
        val snackBar = Snackbar.make(context, view, message, 2000)


        if (bottomNavigationView != null) {
            snackBar.anchorView = bottomNavigationView
        }
        snackBar.show()

    }

}