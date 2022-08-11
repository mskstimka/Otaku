package com.example.a16_rxjava_data.database.models

import androidx.room.TypeConverter
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object AnimePosterConverters {

    @TypeConverter
    fun objectsToStrings(value: List<AnimePosterEntity>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<AnimePosterEntity>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringsToObjects(value: String?): List<AnimePosterEntity>? {
        val gson = Gson()
        val type = object : TypeToken<List<AnimePosterEntity>>() {}.type
        return gson.fromJson(value, type)
    }
}