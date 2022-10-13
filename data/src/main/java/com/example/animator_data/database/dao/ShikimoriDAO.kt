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
    @Insert
    suspend fun insert(localAnimePosterEntity: LocalAnimePosterEntity)


    @Query("DELETE FROM home_posters where id = :id")
    suspend fun delete(id: Int)
    /**
     *  Getting all Anime Poster from database
     *
     *  @return list of Anime Posters
     */
    @Query("SELECT * FROM home_posters")
    fun getAllPosters(): List<LocalAnimePosterEntity>


}


