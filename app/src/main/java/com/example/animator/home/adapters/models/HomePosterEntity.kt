package com.example.animator.home.adapters.models

import androidx.annotation.Keep
import java.util.*

@Keep
class HomePosterEntity : DisplayableItem {
    override val id = UUID.randomUUID().toString()
}