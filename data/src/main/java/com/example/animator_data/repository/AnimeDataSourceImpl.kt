package com.example.animator_data.repository

import com.example.animator_data.database.models.LocalAnimePosterEntity
import com.example.animator_data.database.dao.ShikimoriDAO
import com.example.animator_data.mapper.AnimeDetailsResponseMapper
import com.example.animator_data.mapper.AnimePosterResponseMapper
import com.example.animator_data.network.api.AnimeApi
import com.example.animator_domain.common.Results
import com.example.animator_domain.models.details.AnimeDetailsEntity
import com.example.animator_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.animator_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.animator_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.animator_domain.models.poster.AnimePosterEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class AnimeDataSourceImpl(
    private val animeApi: AnimeApi,
    private val shikimoriDAO: ShikimoriDAO
) : AnimeDataSource {
    override suspend fun getSearchPosters(searchName: String): Results<List<AnimePosterEntity>> {
        return try {
            val response = animeApi.getSearchPosters(search = searchName)
            if (response.isSuccessful) {
                val item =
                    AnimePosterResponseMapper.toListAnimePosterEntity(response.body()!!)
                Results.Success(data = item)
            } else {
                Results.Error(exception = Exception(response.message()))
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }

    override suspend fun getDetails(id: Int): Results<AnimeDetailsEntity> {
        return try {
            val response = animeApi.getDetails(id = id)
            if (response.isSuccessful) {
                val item =
                    AnimeDetailsResponseMapper.toAnimeDetailsEntity(item = response.body()!!)
                Results.Success(data = item)
            } else {
                Results.Error(exception = Exception(response.message()))
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }

    override suspend fun getScreenshots(id: Int): Results<List<AnimeDetailsScreenshotsEntity>> {
        return try {
            val response = animeApi.getScreenshots(id = id)
            if (response.isSuccessful) {
                val list =
                    AnimeDetailsResponseMapper.toAnimeScreenshotsEntity(list = response.body()!!)
                Results.Success(data = list)
            } else {
                if (response.code() == 429) {
                    getScreenshots(id)
                } else {
                    Results.Error(exception = Exception(response.message()))
                }
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }

    override suspend fun getFranchises(id: Int): Results<List<AnimeDetailsFranchisesEntity>> {
        return try {
            val response = animeApi.getFranchises(id = id)
            if (response.isSuccessful) {
                val list =
                    AnimeDetailsResponseMapper.toListAnimeFranchisesEntity(item = response.body()!!)
                Results.Success(data = list)
            } else {
                Results.Error(exception = Exception(response.message()))
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }

    override suspend fun getRoles(id: Int): Results<AnimeDetailsRolesEntity> {
        return try {
            val response = animeApi.getRoles(id = id)
            if (response.isSuccessful) {
                val list = AnimeDetailsResponseMapper.toListAnimeRolesEntity(response.body()!!)
                Results.Success(data = list)
            } else {
                Results.Error(exception = Exception(response.message()))
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }

    override suspend fun getGenrePosters(genreId: Int): Results<List<AnimePosterEntity>> {
        val isLocalNull: Boolean = shikimoriDAO.getPosterFromIdGenre(id = genreId) == null

        return try {
            val response = animeApi.getGenrePosters(genreId = genreId)

            when (response.isSuccessful) {
                true -> {
                    val list =
                        AnimePosterResponseMapper.toListAnimePosterEntity(list = response.body()!!)

                    if (isLocalNull) {
                        shikimoriDAO.insert(LocalAnimePosterEntity(list = list, id = genreId))
                    } else {
                        shikimoriDAO.update(list = list, id = genreId)
                    }
                    Results.Success(data = list)
                }
                false -> {
                    if (response.code() == 429) {
                        getGenrePosters(genreId)
                    } else if (isLocalNull) {
                        Results.Error(exception = Exception(response.message()))
                    } else {
                        Results.Success(data = shikimoriDAO.getPosterFromIdGenre(id = genreId)!!.list)
                    }
                }
            }

        } catch (e: Exception) {
            if (isLocalNull) {
                Results.Error(exception = e)
            } else {
                Results.Success(data = shikimoriDAO.getPosterFromIdGenre(id = genreId)!!.list)
            }
        }
    }

    override suspend fun getRandomPoster(
        limit: Int,
        censored: Boolean,
        order: String
    ): Results<List<AnimePosterEntity>> {

        return try {
            val response = animeApi.getRandom()

            when (response.isSuccessful) {
                true -> {
                    val list =
                        AnimePosterResponseMapper.toListAnimePosterEntity(list = response.body()!!)

                    Results.Success(data = list)
                }
                false -> {
                    if (response.code() == 429) {
                        getRandomPoster(1, true, "random")
                    } else
                        Results.Error(exception = Exception(response.message()))
                }
            }

        } catch (e: Exception) {
            Results.Error(exception = e)

        }
    }
}
