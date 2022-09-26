package com.example.animator_data.network.api

import com.example.animator_data.network.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import io.reactivex.Observable

interface AnimeApi {

    /**
     * Getting Anime Posters on Search
     *
     * @param search - the searchText
     * @param limit - limit of the posters
     * @param censored - not 18+ rating
     *
     * @return Observable list of Anime Posters
     * */
    @GET("api/animes")
    fun getSearchPosters(
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
    suspend fun getDetails(
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
    suspend fun getGenrePosters(
        @Query(value = "genre") genreId: Int = 0,
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
    suspend fun getScreenshots(
        @Path(value = "id") id: Int
    ): Response<List<AnimeDetailsScreenshotsEntityResponse>>

    /**
     * Getting connected animes for id
     *
     * @param id - id of anime
     *
     * @return Response connected animes in object
     */
    @GET("api/animes/{id}/franchise")
    suspend fun getFranchises(
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
    suspend fun getRoles(
        @Path(value = "id") id: Int
    ): Response<List<AnimeDetailsRolesEntityResponse>>


    /**
     * Getting Preview Posters (random)
     *
     * @param limit - limit of the posters
     * @param censored - not 18+ rating
     * @param order - sorted mode
     *
     * @return Response list of Anime Posters
     */
    @GET("api/animes")
    suspend fun getRandom(
        @Query(value = "limit") limit: Int = 1,
        @Query(value = "censored") censored: Boolean = true,
        @Query(value = "order") order: String = "random"
    ): Response<List<AnimePosterEntityResponse>>
}