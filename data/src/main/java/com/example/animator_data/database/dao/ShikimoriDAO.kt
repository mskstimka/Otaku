package com.example.animator_data.database.dao

import androidx.room.*
import com.example.animator_data.database.models.LocalAnimePosterEntity
import com.example.animator_domain.models.poster.AnimePosterEntity

@Dao
interface ShikimoriDAO {

    /**
     * Inserts object of Anime Posters in database
     *
     * @param localAnimePosterEntity - object of Anime Posters
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(localAnimePosterEntity: LocalAnimePosterEntity)

    /**
     * Updates the list of Anime Posters on ID in database
     *
     * @param id - genre id
     * @param list - list of Anime Posters
     */
    @Query("UPDATE home_posters SET list = :list where id = :id")
    suspend fun update(id: Int, list: List<AnimePosterEntity>)

    /**
     *  Getting all Anime Poster from database
     *
     *  @return list of Anime Posters
     */
    @Query("SELECT * FROM home_posters ORDER BY id ASC")
    fun getAllPosters(): List<LocalAnimePosterEntity>

    /**
     * Getting Anime Posters on genre id
     *
     * @param id - genre id
     *
     * @return Anime Posters in object
     */
    @Query("SELECT * FROM home_posters WHERE id = :id")
    fun getPosterFromIdGenre(id: Int): LocalAnimePosterEntity?
}


