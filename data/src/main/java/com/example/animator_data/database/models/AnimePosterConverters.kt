package com.example.animator_data.database.models

import androidx.room.TypeConverter
import com.example.animator_domain.models.Image
import com.example.animator_domain.models.poster.AnimePosterEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object AnimePosterConverters {

    @TypeConverter
    fun objectsToStrings(value: Image): String {
        val gson = Gson()
        val type = object : TypeToken<Image>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringsToObjects(value: String): Image {
        val gson = Gson()
        val type = object : TypeToken<Image>() {}.type
        return gson.fromJson(value, type)
    }
}