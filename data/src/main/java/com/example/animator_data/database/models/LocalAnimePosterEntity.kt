package com.example.animator_data.database.models

import androidx.annotation.Keep
import androidx.room.*
import com.example.animator_domain.models.poster.AnimePosterEntity

@Keep
@Entity(tableName = "home_posters")
data class LocalAnimePosterEntity(
    @TypeConverters(AnimePosterConverters::class) val list: List<AnimePosterEntity>,
    @PrimaryKey(autoGenerate = false) val id: Int = 0
)