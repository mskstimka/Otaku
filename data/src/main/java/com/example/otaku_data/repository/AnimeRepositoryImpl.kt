package com.example.otaku_data.repository

import com.example.otaku_data.repository.sources.anime.AnimeDataSource
import com.example.otaku_data.repository.sources.watch.WatchDataSource
import com.example.otaku_data.utils.SharedPreferencesHelper
import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.PersonEntity
import com.example.otaku_domain.models.characters.CharacterDetailsEntity
import com.example.otaku_domain.models.details.AnimeDetailsEntity
import com.example.otaku_domain.models.details.Translations
import com.example.otaku_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.otaku_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.otaku_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.otaku_domain.models.poster.AnimePosterEntity
import com.example.otaku_domain.repository.AnimeRepository
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeDataSource: AnimeDataSource,
    private val watchDataSourceImpl: WatchDataSource,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
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
        return animeDataSource.getGenrePosters(
            genreId = genreId,
            isCensored = sharedPreferencesHelper.getIsCensoredSearch()
        )
    }

    override suspend fun getRandomPoster(
        limit: Int,
        censored: Boolean,
        order: String
    ): Results<List<AnimePosterEntity>> {
        return animeDataSource.getRandomPoster(
            limit = limit,
            censored = sharedPreferencesHelper.getIsCensoredSearch(),
            order = order
        )
    }

    override suspend fun getCharacters(id: Int): Results<CharacterDetailsEntity> {
        return animeDataSource.getCharacters(id = id)
    }

    override suspend fun getPersons(id: Int): Results<PersonEntity> {
        return animeDataSource.getPersons(id = id)
    }

    override suspend fun createFavorite(linkedId: Long, linkedType: String): Results<String> {
        return animeDataSource.createFavorite(linkedId = linkedId, linkedType = linkedType)
    }

    override suspend fun deleteFavorite(linkedId: Long, linkedType: String): Results<String> {
        return animeDataSource.deleteFavorite(linkedId = linkedId, linkedType = linkedType)
    }

}