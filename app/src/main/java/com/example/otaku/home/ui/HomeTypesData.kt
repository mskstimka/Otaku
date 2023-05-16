package com.example.otaku.home.ui

import com.google.errorprone.annotations.Keep

@Keep
data class HomeTypesData<T1, T2, T3>(
    val random: T2,
    val randomScreenshots: T3
)