package com.example.animator_domain.repository

import com.example.animator_domain.common.Results
import com.example.animator_domain.models.PersonEntity
import com.example.animator_domain.models.characters.CharacterDetailsEntity
import com.example.animator_domain.models.details.AnimeDetailsEntity
import com.example.animator_domain.models.details.Translations
import com.example.animator_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.animator_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.animator_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.animator_domain.models.poster.AnimePosterEntity

interface AnimeRepository {

    /**
     * Getting Video Episode of Anime
     *
     * @param malId - id of anime
     * @param name - name of anime
     * @param episode - episode count
     * @param kind - kind anime
     *
     * @return Results list of TranslationResponse
     * */
    suspend fun getVideo(
        malId: Long,
        episode: Int,
        name: String,
        kind: String
    ): Results<List<Translations>>


    /**
     * Getting count of Episodes of Anime
     *
     * @param malId - id of anime
     * @param name - name of anime
     *
     * @return Results of Integer
     * */
    suspend fun getSeries(malId: Long, name: String): Results<Int>


    /**
     * Getting Anime Posters from Search
     *
     * @param searchName - the search text
     *
     * @return Results list of Anime Posters
     */
    suspend fun getSearchPosters(searchName: String): Results<List<AnimePosterEntity>>

    /**
     * Getting Details of Anime on id
     *
     * @param id - id of anime
     *
     * @return Results of Anime Details (object) or Results of error
     */
    suspend fun getDetails(id: Int): Results<AnimeDetailsEntity>

    /**
     * Getting Screenshots of Anime on id
     *
     * @param id - id of anime
     *
     * @return Results of list of Anime Screenshots or Results of error
     */
    suspend fun getScreenshots(id: Int): Results<List<AnimeDetailsScreenshotsEntity>>

    /**
     * Getting connected animes for id
     *
     * @param id - id of anime
     *
     * @return Results of connected animes in object or Results of error
     */
    suspend fun getFranchises(id: Int): Results<List<AnimeDetailsFranchisesEntity>>

    /**
     * Getting Characters and Authors of Anime on id
     *
     * @param id - id of anime
     *
     * @return Results object of Anime Characters and Authors or Results of error
     */
    suspend fun getRoles(id: Int): Results<AnimeDetailsRolesEntity>

    /**
     * /**
     * Getting Preview Posters on genreId
     *
     * @param genreId - id genre
     *
     * @return Results of list of Anime Posters or Results of error
    */
     */
    suspend fun getGenrePosters(genreId: Int): Results<List<AnimePosterEntity>>


    /**
     * Getting Preview Posters on genreId
     *
     * @param limit - limit of the posters
     * @param censored - not 18+ rating
     * @param order - sorted mode     *
     * @return Results of list of Anime Posters or Results of error
     *
     */
    suspend fun getRandomPoster(
        limit: Int,
        censored: Boolean,
        order: String
    ): Results<List<AnimePosterEntity>>

    /**
     * Getting details of Character
     *
     * @param id - id of character
     *
     * @return Results item of Characters info
     */
    suspend fun getCharacters(
        id: Int
    ): Results<CharacterDetailsEntity>


    /**
     * Getting details of Person
     *
     * @param id - id of character
     *
     * @return Results item of Person info
     */
    suspend fun getPersons(
        id: Int
    ): Results<PersonEntity>


    /**
     * Adding anime to Local Store
     *
     * @param item - entity of Anime Poster
     */
    suspend fun addLocalFavorites(item: AnimePosterEntity)

    /**
     * Deleting anime in Local Store
     *
     * @param id - id of Anime
     */
    suspend fun deleteLocalFavorites(id: Int)

    /**
     * Getting list of anime in to Local Store
     *
     * @return list of AnimePosterEntity
     */
    fun getLocalFavorites(): List<AnimePosterEntity>

    /**
     * Checking anime to Local Store
     *
     * @param id - id of anime
     */
    fun checkIsFavorite(id: Int): Boolean


}