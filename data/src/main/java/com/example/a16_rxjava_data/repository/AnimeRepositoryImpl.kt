package com.example.a16_rxjava_data.repository

import com.example.a16_rxjava_domain.common.Results
import com.example.a16_rxjava_domain.models.details.AnimeDetailsEntity
import com.example.a16_rxjava_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.a16_rxjava_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.a16_rxjava_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import com.example.a16_rxjava_domain.repository.AnimeRepository
import io.reactivex.Observable

class AnimeRepositoryImpl(private val animeDataSource: AnimeDataSource) : AnimeRepository {

    override fun getAnimePostersFromSearch(searchName: String): Observable<List<AnimePosterEntity>> {
        return animeDataSource.getAnimePostersFromSearch(searchName = searchName)
    }

    override suspend fun getAnimeDetailsFromId(id: Int): Results<AnimeDetailsEntity> {
        return animeDataSource.getAnimeDetailsFromId(id = id)
    }

    override suspend fun getAnimeScreenshotsFromId(id: Int): Results<List<AnimeDetailsScreenshotsEntity>> {
        return animeDataSource.getAnimeScreenshotsFromId(id = id)
    }

    override suspend fun getAnimeFranchisesFromId(id: Int): Results<List<AnimeDetailsFranchisesEntity>> {
        return animeDataSource.getAnimeFranchisesFromId(id = id)
    }

    override suspend fun getAnimeRolesFromId(id: Int): Results<List<AnimeDetailsRolesEntity>> {
        return animeDataSource.getAnimeRolesFromId(id = id)
    }

    override suspend fun getAnimePrevPostersFromGenres(genreId: Int): Results<List<AnimePosterEntity>> {
        return animeDataSource.getAnimePrevPostersFromGenres(genreId = genreId)
    }
}