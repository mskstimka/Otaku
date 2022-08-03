package com.example.a16_rxjava_data.repository


import com.example.a16_rxjava_domain.common.Results
import com.example.a16_rxjava_domain.models.details.AnimeDetailsEntity
import com.example.a16_rxjava_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.a16_rxjava_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.a16_rxjava_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import io.reactivex.Observable

interface AnimeDataSource {
    fun getAnimePostersFromSearch(searchName: String): Observable<List<AnimePosterEntity>>

    suspend fun getAnimeDetailsFromId(id: Int): Results<AnimeDetailsEntity>

    suspend fun getAnimeScreenshotsFromId(id: Int): Results<List<AnimeDetailsScreenshotsEntity>>

    suspend fun getAnimeFranchisesFromId(id: Int): Results<List<AnimeDetailsFranchisesEntity>>

    suspend fun getAnimeRolesFromId(id: Int): Results<List<AnimeDetailsRolesEntity>>

}