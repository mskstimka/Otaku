package com.example.otaku_data.repository.sources.anime

import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.PersonEntity
import com.example.otaku_domain.models.characters.CharacterDetailsEntity
import com.example.otaku_domain.models.details.AnimeDetailsEntity
import com.example.otaku_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.otaku_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.otaku_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.otaku_domain.models.poster.AnimePosterEntity
import com.example.otaku_domain.models.user.status.UserRate


interface AnimeDataSource {

    /**
     * Getting Anime Posters from Search
     *
     * @param searchName - the search text
     *
     * @return Results list of Anime Posters
     */
    suspend fun getSearchPosters(
        searchName: String,
        isCensored: Boolean
    ): Results<List<AnimePosterEntity>>

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
     * @return Results of list of Anime Characters and Authors or Results of error
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
    suspend fun getGenrePosters(genreId: Int, isCensored: Boolean): Results<List<AnimePosterEntity>>


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