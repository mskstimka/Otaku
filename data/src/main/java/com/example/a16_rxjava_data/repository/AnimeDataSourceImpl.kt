package com.example.a16_rxjava_data.repository

import com.example.a16_rxjava_data.database.models.LocalAnimePosterEntity
import com.example.a16_rxjava_data.database.dao.ShikimoriDAO
import com.example.a16_rxjava_data.mapper.AnimeDetailsResponseMapper
import com.example.a16_rxjava_data.mapper.AnimePosterResponseMapper
import com.example.a16_rxjava_data.network.api.ShikimoriAPI
import com.example.a16_rxjava_domain.common.Results
import com.example.a16_rxjava_domain.models.details.AnimeDetailsEntity
import com.example.a16_rxjava_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.a16_rxjava_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.a16_rxjava_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class AnimeDataSourceImpl(
    private val shikimoriAPI: ShikimoriAPI,
    private val shikimoriDAO: ShikimoriDAO
) : AnimeDataSource {
    override fun getAnimePostersFromSearch(searchName: String): Observable<List<AnimePosterEntity>> {
        return shikimoriAPI.getAnimePostersFromSearch(search = searchName)
            .subscribeOn(Schedulers.io())
            .map { AnimePosterResponseMapper.toListAnimePosterEntity(it) }
    }

    override suspend fun getAnimeDetailsFromId(id: Int): Results<AnimeDetailsEntity> {
        return try {
            val response = shikimoriAPI.getAnimeDetailsFromId(id = id)
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

    override suspend fun getAnimeScreenshotsFromId(id: Int): Results<List<AnimeDetailsScreenshotsEntity>> {
        return try {
            val response = shikimoriAPI.getAnimeScreenshotsFromId(id = id)
            if (response.isSuccessful) {
                val list =
                    AnimeDetailsResponseMapper.toAnimeScreenshotsEntity(list = response.body()!!)
                Results.Success(data = list)
            } else {
                Results.Error(exception = Exception(response.message()))
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }

    override suspend fun getAnimeFranchisesFromId(id: Int): Results<List<AnimeDetailsFranchisesEntity>> {
        return try {
            val response = shikimoriAPI.getAnimeFranchisesFromId(id = id)
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

    override suspend fun getAnimeRolesFromId(id: Int): Results<List<AnimeDetailsRolesEntity>> {
        return try {
            val response = shikimoriAPI.getAnimeRolesFromId(id = id)
            if (response.isSuccessful) {
                val list = response.body()!!.map { item ->
                    AnimeDetailsResponseMapper.toAnimeRolesEntity(item = item)
                }
                Results.Success(data = list)
            } else {
                Results.Error(exception = Exception(response.message()))
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }

    override suspend fun getAnimePrevPostersFromGenres(genreId: Int): Results<List<AnimePosterEntity>> {
        val isLocalNull: Boolean = shikimoriDAO.getPosterFromIdGenre(id = genreId) == null

        return try {
            val response = shikimoriAPI.getAnimePrevPostersFromGenre(genreId)

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
                    if (isLocalNull) {
                        Results.Error(exception = Exception(response.message()))
                    } else {
                        Results.Success(data = shikimoriDAO.getPosterFromIdGenre(genreId)!!.list)
                    }
                }
            }

        } catch (e: Exception) {
            if (isLocalNull) {
                Results.Error(exception = e)
            } else {
                Results.Success(data = shikimoriDAO.getPosterFromIdGenre(genreId)!!.list)
            }
        }
    }

}
