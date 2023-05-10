package com.example.otaku_data.database.models

import androidx.annotation.Keep
import androidx.room.*
import com.example.otaku_domain.models.Image

@Keep
@Entity(tableName = "home_posters")
data class LocalAnimePosterEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @TypeConverters(AnimePosterConverters::class)  val image: Image,
    val name: String,
    val score: String,
    val episodes: Int,
    val episodesAired: Int,
    val url: String,
    val status: String,
    val russian: String
)