package com.example.otaku.user.ui.viewmodel.models

data class UserTypesData<T1, T2, T3, T4, T5>(
    val brief: T1,
    val favorites: T2,
    val stats: T3,
    val friends: T4,
    val history: T5
)