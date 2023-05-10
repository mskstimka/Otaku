package com.example.otaku_domain.models.user

import androidx.annotation.Keep

@Keep
data class FavoriteList(
        val animes: List<Favorite>,
        val mangas: List<Favorite>,
        val characters: List<Favorite>,
        val people: List<Favorite>,
        val mangakas: List<Favorite>,
        val seyu: List<Favorite>,
        val producers: List<Favorite>,
        val all: List<Favorite>
)