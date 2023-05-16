package com.example.otaku.user.rates.anime

import com.google.errorprone.annotations.Keep

@Keep
data class HeaderUserRates(val id: String = "HEADER", val headerText: String)