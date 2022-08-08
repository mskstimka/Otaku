package com.example.a16_rxjava_data.network

import com.example.a16_rxjava_data.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import io.reactivex.Observable

interface ShikimoriAPI {

    @GET("api/animes")
    fun getAnimePostersFromSearch(
        @Query(value = "search", encoded = true) search: String = "",
        @Query(value = "limit", encoded = true) limit: Int = 20,
        @Query(value = "censored", encoded = true) censored: Boolean = true
    ): Observable<List<AnimePosterEntityResponse>>

    @GET("api/animes/{id}")
    suspend fun getAnimeDetailsFromId(
        @Path(value = "id", encoded = true) id: Int
    ): Response<AnimeDetailsEntityResponse>

    @GET("api/animes")
    suspend fun getAnimePrevPostersFromGenres(
        @Query(value = "genre", encoded = true) genre: List<Int> = emptyList(),
        @Query(value = "limit", encoded = true) limit: Int = 20,
        @Query(value = "censored", encoded = true) censored: Boolean = true,
        @Query(value = "order", encoded = true) order: String = "random"
    ): Response<List<AnimePosterEntityResponse>>

    @GET("api/animes/{id}/screenshots")
    suspend fun getAnimeScreenshotsFromId(
        @Path(value = "id", encoded = true) id: Int
    ): Response<List<AnimeDetailsScreenshotsEntityResponse>>

    @GET("api/animes/{id}/franchise")
    suspend fun getAnimeFranchisesFromId(
        @Path(value = "id", encoded = true) id: Int
    ): Response<AnimeDetailsFranchisesEntityResponse>

    @GET("api/animes/{id}/roles")
    suspend fun getAnimeRolesFromId(
        @Path(value = "id", encoded = true) id: Int
    ): Response<List<AnimeDetailsRolesEntityResponse>>
}
