package com.example.otaku_domain.models.user.history
import org.joda.time.DateTime


data class UserHistory(
    val id: Long,
    val dateCreated: DateTime,
    val description: String,
    val target: LinkedContent?
)