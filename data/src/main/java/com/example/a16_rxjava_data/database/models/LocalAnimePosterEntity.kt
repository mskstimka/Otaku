package com.example.a16_rxjava_data.database.models

import androidx.room.*
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity


@Entity(tableName = "home_posters")
data class LocalAnimePosterEntity(
    @TypeConverters(AnimePosterConverters::class) val list: List<AnimePosterEntity>,
    @PrimaryKey(autoGenerate = false) val id: Int = 0
)