package com.example.animator_data.repository


import com.example.animator_domain.common.Results
import com.example.animator_domain.models.details.AnimeDetailsEntity
import com.example.animator_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.animator_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.animator_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.animator_domain.models.poster.AnimePosterEntity
import io.reactivex.Observable
import retrofit2.http.Query

interface AnimeDataSource {

    /**
     * Getting Anime Posters from Search
     *
     * @param searchName - the search text
     *
     * @return Observable list of Anime Posters
     */
    fun getSearchPosters(searchName: String): Observable<List<AnimePosterEntity>>

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

}