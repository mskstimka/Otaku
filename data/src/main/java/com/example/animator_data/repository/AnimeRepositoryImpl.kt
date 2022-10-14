package com.example.animator_data.repository

import com.example.animator_domain.common.Results
import com.example.animator_domain.models.PersonEntity
import com.example.animator_domain.models.characters.CharacterDetailsEntity
import com.example.animator_domain.models.details.AnimeDetailsEntity
import com.example.animator_domain.models.details.Translations
import com.example.animator_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.animator_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.animator_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.animator_domain.models.poster.AnimePosterEntity
import com.example.animator_domain.repository.AnimeRepository

class AnimeRepositoryImpl(
    private val animeDataSource: AnimeDataSource,
    private val watchDataSourceImpl: WatchDataSource
) : AnimeRepository {
    override suspend fun getVideo(
        malId: Long,
        episode: Int,
        name: String,
        kind: String
    ): Results<List<Translations>> {
        return watchDataSourceImpl.getVideo(
            malId = malId, episode = episode, name = name, kind = kind
        )
    }

    override suspend fun getSeries(malId: Long, name: String): Results<Int> {
        return watchDataSourceImpl.getSeries(malId, name)
    }

    override suspend fun getSearchPosters(searchName: String): Results<List<AnimePosterEntity>> {
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

    override suspend fun getCharacters(id: Int): Results<CharacterDetailsEntity> {
        return animeDataSource.getCharacters(id = id)
    }

    override suspend fun getPersons(id: Int): Results<PersonEntity> {
        return animeDataSource.getPersons(id = id)
    }

    override suspend fun addLocalFavorites(item: AnimePosterEntity) {
        return animeDataSource.addLocalFavorites(item = item)
    }

    override suspend fun deleteLocalFavorites(id: Int) {
        return animeDataSource.deleteLocalFavorites(id = id)
    }

    override fun getLocalFavorites(): List<AnimePosterEntity> {
        return animeDataSource.getLocalFavorites()
    }

    override fun checkIsFavorite(id: Int): Boolean {
        return animeDataSource.checkIsFavorite(id = id)
    }

}