package com.example.animator_data.network.api

import com.example.animator_data.network.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import io.reactivex.Observable

interface ShikimoriAPI {

    /**
     * Getting Anime Posters from Search
     *
     * @param search - the searchText
     * @param limit - limit of the posters
     * @param censored - not 18+ rating
     *
     * @return Observable list of Anime Posters
     * */
    @GET("api/animes")
    fun getAnimePostersFromSearch(
        @Query(value = "search") search: String = "",
        @Query(value = "limit") limit: Int = 20,
        @Query(value = "censored") censored: Boolean = true
    ): Observable<List<AnimePosterEntityResponse>>

    /**
     * Getting Details of Anime on id
     *
     * @param id - id of anime
     *
     * @return Response of Anime Details (object)
     */
    @GET("api/animes/{id}")
    suspend fun getAnimeDetailsFromId(
        @Path(value = "id") id: Int
    ): Response<AnimeDetailsEntityResponse>

    /**
     * Getting Preview Posters on genreId
     *
     * @param genreId - id genre
     * @param limit - limit of the posters
     * @param censored - not 18+ rating
     * @param order - sorted mode
     *
     * @return Response list of Anime Posters
     */
    @GET("api/animes")
    suspend fun getAnimePrevPostersFromGenre(
        @Query(value = "genre") genreId: Int,
        @Query(value = "limit") limit: Int = 20,
        @Query(value = "censored") censored: Boolean = true,
        @Query(value = "order") order: String = "random"
    ): Response<List<AnimePosterEntityResponse>>

    /**
     * Getting Screenshots of Anime on id
     *
     * @param id - id of anime
     *
     * @return Response list of Anime Screenshots
     */
    @GET("api/animes/{id}/screenshots")
    suspend fun getAnimeScreenshotsFromId(
        @Path(value = "id") id: Int
    ): Response<List<AnimeDetailsScreenshotsEntityResponse>>

    /**
     * Getting similar animes for id
     *
     * @param id - id of anime
     *
     * @return Response similar animes in object
     */
    @GET("api/animes/{id}/franchise")
    suspend fun getAnimeFranchisesFromId(
        @Path(value = "id") id: Int
    ): Response<AnimeDetailsFranchisesEntityResponse>

    /**
     * Getting Characters and Authors of Anime on id
     *
     * @param id - id of anime
     *
     * @return Response list of Anime Characters and Authors
     */
    @GET("api/animes/{id}/roles")
    suspend fun getAnimeRolesFromId(
        @Path(value = "id") id: Int
    ): Response<List<AnimeDetailsRolesEntityResponse>>
}
