package com.example.animator_data.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.animator_data.database.models.AnimePosterConverters
import com.example.animator_data.database.models.LocalAnimePosterEntity
import com.example.animator_domain.DATABASE_SHIKIMORI
import javax.inject.Singleton


@Database(entities = [LocalAnimePosterEntity::class], version = 1, exportSchema = false)
@TypeConverters(AnimePosterConverters::class)
@Singleton
abstract class ShikimoriDataBase : RoomDatabase() {

    abstract fun getCurrencyDao(): ShikimoriDAO

    companion object {
        @Volatile
        private var INSTANCE: ShikimoriDataBase? = null

        fun getDatabase(context: Context): ShikimoriDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShikimoriDataBase::class.java,
                    DATABASE_SHIKIMORI
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}