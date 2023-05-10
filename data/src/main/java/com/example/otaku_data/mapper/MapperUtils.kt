package com.example.otaku_data.mapper

import com.example.otaku_domain.*
import com.example.otaku_domain.models.details.Genre
import com.example.otaku_domain.models.details.Studio
import com.example.otaku_domain.models.details.Video

object MapperUtils {

    fun checkStatusColor(status: String?): String = when (status) {
        ONGOING_STATUS -> BLUE_STATUS_COLOR
        ANONS_STATUS -> RED_STATUS_COLOR
        else -> GREEN_STATUS_COLOR
    }

    fun checkVideoList(list: List<Video>): List<Video> {
        return when (list) {
            emptyList<Video>() -> listOf(DEFAULT_VIDEO)
            else -> list
        }
    }

    fun checkStudioList(list: List<Studio>): List<Studio> {
        return when (list) {
            emptyList<Studio>() -> listOf(DEFAULT_STUDIO)
            else -> list
        }
    }

    fun checkGenresAdapter(list: List<Genre>): List<Genre> {
        return when (list) {
            emptyList<Genre>() -> listOf(DEFAULT_GENRE)
            else -> list
        }
    }

}

