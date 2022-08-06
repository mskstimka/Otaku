package com.example.rxjava.home.adapters

import java.util.*

data class PrevTitle(
    val title: String
) : DisplayableItem {
    override val id = UUID.randomUUID().toString()
}