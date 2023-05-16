package com.example.otaku.utils

object CountUtils {

    private var count = 0

    fun getId(): Int {
        val count = count
        this.count++
        return count
    }
}