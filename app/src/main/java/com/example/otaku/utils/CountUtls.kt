package com.example.otaku.utils

import android.util.Log

object CountUtls {

    private var count = 0

    fun getId(): Int {
        val count = count
        this.count++
        Log.d(count.toString(), "COUNT")
        return count
    }
}