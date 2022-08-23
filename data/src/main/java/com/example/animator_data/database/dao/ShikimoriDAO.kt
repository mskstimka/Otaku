package com.example.animator_data.database.dao

import androidx.room.*
import com.example.animator_data.database.models.LocalAnimePosterEntity
import com.example.animator_domain.models.poster.AnimePosterEntity

@Dao
interface ShikimoriDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(localAnimePosterEntity: LocalAnimePosterEntity)

    @Query("UPDATE home_posters SET list = :list where id = :id")
    suspend fun update(id: Int, list: List<AnimePosterEntity>)

    @Query("SELECT * FROM home_posters ORDER BY id ASC")
    fun getAllPosters(): List<LocalAnimePosterEntity>

    @Query("SELECT * FROM home_posters WHERE id = :id")
    fun getPosterFromIdGenre(id: Int): LocalAnimePosterEntity?
}


