package com.example.otaku_data.database.dao

import androidx.room.*
import com.example.otaku_data.database.models.LocalAnimePosterEntity

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


