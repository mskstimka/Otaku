package com.example.animator_data.network.api

import com.example.animator_data.network.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import io.reactivex.Observable

interface ShikimoriAPI {

    @GET("api/animes")
    fun getAnimePostersFromSearch(
        @Query(value = "search") search: String = "",
        @Query(value = "limit") limit: Int = 20,
        @Query(value = "censored") censored: Boolean = true
    ): Observable<List<AnimePosterEntityResponse>>

    @GET("api/animes/{id}")
    suspend fun getAnimeDetailsFromId(
        @Path(value = "id") id: Int
    ): Response<AnimeDetailsEntityResponse>

    @GET("api/animes")
    suspend fun getAnimePrevPostersFromGenre(
        @Query(value = "genre") genreId: Int,
        @Query(value = "limit") limit: Int = 20,
        @Query(value = "censored") censored: Boolean = true,
        @Query(value = "order") order: String = "random"
    ): Response<List<AnimePosterEntityResponse>>

    @GET("api/animes/{id}/screenshots")
    suspend fun getAnimeScreenshotsFromId(
        @Path(value = "id") id: Int
    ): Response<List<AnimeDetailsScreenshotsEntityResponse>>

    @GET("api/animes/{id}/franchise")
    suspend fun getAnimeFranchisesFromId(
        @Path(value = "id") id: Int
    ): Response<AnimeDetailsFranchisesEntityResponse>

    @GET("api/animes/{id}/roles")
    suspend fun getAnimeRolesFromId(
        @Path(value = "id") id: Int
    ): Response<List<AnimeDetailsRolesEntityResponse>>
}
