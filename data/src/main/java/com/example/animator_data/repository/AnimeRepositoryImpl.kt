package com.example.animator_data.repository

import com.example.animator_domain.common.Results
import com.example.animator_domain.models.details.AnimeDetailsEntity
import com.example.animator_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.animator_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.animator_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.animator_domain.models.poster.AnimePosterEntity
import com.example.animator_domain.repository.AnimeRepository
import io.reactivex.Observable

class AnimeRepositoryImpl(private val animeDataSource: AnimeDataSource) : AnimeRepository {

    override fun getSearchPosters(searchName: String): Observable<List<AnimePosterEntity>> {
        return animeDataSource.getSearchPosters(searchName = searchName)
    }

    override suspend fun getDetails(id: Int): Results<AnimeDetailsEntity> {
        return animeDataSource.getDetails(id = id)
    }

    override suspend fun getScreenshots(id: Int): Results<List<AnimeDetailsScreenshotsEntity>> {
        return animeDataSource.getScreenshots(id = id)
    }

    override suspend fun getFranchises(id: Int): Results<List<AnimeDetailsFranchisesEntity>> {
        return animeDataSource.getFranchises(id = id)
    }

    override suspend fun getRoles(id: Int): Results<AnimeDetailsRolesEntity> {
        return animeDataSource.getRoles(id = id)
    }

    override suspend fun getGenrePosters(genreId: Int): Results<List<AnimePosterEntity>> {
        return animeDataSource.getGenrePosters(genreId = genreId)
    }

    override suspend fun getRandomPoster(
        limit: Int,
        censored: Boolean,
        order: String
    ): Results<List<AnimePosterEntity>> {
        return animeDataSource.getRandomPoster(limit = limit, censored = censored, order = order)
    }
}