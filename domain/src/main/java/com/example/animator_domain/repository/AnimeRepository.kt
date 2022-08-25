package com.example.animator_domain.repository

import com.example.animator_domain.common.Results
import com.example.animator_domain.models.details.AnimeDetailsEntity
import com.example.animator_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.animator_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.animator_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.animator_domain.models.poster.AnimePosterEntity
import io.reactivex.Observable

interface AnimeRepository {

    /**
     * Getting Anime Posters from Search
     *
     * @param searchName - the search text
     *
     * @return Observable list of Anime Posters
     */
    fun getAnimePostersFromSearch(searchName: String): Observable<List<AnimePosterEntity>>

    /**
     * Getting Details of Anime on id
     *
     * @param id - id of anime
     *
     * @return Results of Anime Details (object) or Results of error
     */
    suspend fun getAnimeDetailsFromId(id: Int): Results<AnimeDetailsEntity>

    /**
     * Getting Screenshots of Anime on id
     *
     * @param id - id of anime
     *
     * @return Results of list of Anime Screenshots or Results of error
     */
    suspend fun getAnimeScreenshotsFromId(id: Int): Results<List<AnimeDetailsScreenshotsEntity>>

    /**
     * Getting similar animes for id
     *
     * @param id - id of anime
     *
     * @return Results of similar animes in object or Results of error
     */
    suspend fun getAnimeFranchisesFromId(id: Int): Results<List<AnimeDetailsFranchisesEntity>>

    /**
     * Getting Characters and Authors of Anime on id
     *
     * @param id - id of anime
     *
     * @return Results of list of Anime Characters and Authors or Results of error
     */
    suspend fun getAnimeRolesFromId(id: Int): Results<List<AnimeDetailsRolesEntity>>

    /**
     * /**
     * Getting Preview Posters on genreId
     *
     * @param genreId - id genre
     *
     * @return Results of list of Anime Posters or Results of error
    */
     */
    suspend fun getAnimePrevPostersFromGenres(genreId: Int): Results<List<AnimePosterEntity>>
}