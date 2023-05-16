package com.example.otaku.home.adapters.poster

import androidx.annotation.DrawableRes
import com.google.errorprone.annotations.Keep
import java.util.*

@Keep
data class NewsPoster(
    val id: String = UUID.randomUUID().toString(),
    val action: () -> Unit,
    val textId: Int,
    @DrawableRes val imageId: Int,
    val buttonTextId: Int,
    val buttonColor: Int? = null
)