package com.example.animator_data.repository.sources.anime

import com.example.animator_data.database.dao.ShikimoriDAO
import com.example.animator_data.mapper.*
import com.example.animator_data.network.api.AnimeApi
import com.example.animator_domain.common.Results
import com.example.animator_domain.models.PersonEntity
import com.example.animator_domain.models.characters.CharacterDetailsEntity
import com.example.animator_domain.models.details.AnimeDetailsEntity
import com.example.animator_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.animator_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.animator_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.animator_domain.models.poster.AnimePosterEntity

class AnimeDataSourceImpl(
    private val animeApi: AnimeApi,
    private val shikimoriDAO: ShikimoriDAO
) : AnimeDataSource {


    override suspend fun getSearchPosters(
        searchName: String,
        isCensored: Boolean
    ): Results<List<AnimePosterEntity>> {
        return try {
            val response = animeApi.getSearchPosters(search = searchName, censored = isCensored)
            if (response.isSuccessful) {
                val item = response.body()!!.toListAnimePosterEntity()
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
                val item = response.body()!!.toAnimeDetailsEntity()
                Results.Success(data = item)
            } else {
                if (response.code() == 429) {
                    getDetails(id)
                } else {
                    Results.Error(exception = Exception(response.message()))
                }
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }

    override suspend fun getScreenshots(id: Int): Results<List<AnimeDetailsScreenshotsEntity>> {
        return try {
            val response = animeApi.getScreenshots(id = id)
            if (response.isSuccessful) {
                val list = response.body()!!.toAnimeDetailsScreenshotsEntity()
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
                val list = response.body()!!.toListAnimeDetailsFranchisesEntity()
                Results.Success(data = list)
            } else {
                if (response.code() == 429) {
                    getFranchises(id)
                } else {
                    Results.Error(exception = Exception(response.message()))
                }
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }

    override suspend fun getRoles(id: Int): Results<AnimeDetailsRolesEntity> {
        return try {
            val response = animeApi.getRoles(id = id)
            if (response.isSuccessful) {
                val list = response.body()!!.toListAnimeRolesEntity()
                Results.Success(data = list)
            } else {
                if (response.code() == 429) {
                    getRoles(id)
                } else {
                    Results.Error(exception = Exception(response.message()))
                }
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }

    override suspend fun getGenrePosters(
        genreId: Int,
        isCensored: Boolean
    ): Results<List<AnimePosterEntity>> {

        return try {
            val response = animeApi.getGenrePosters(genreId = genreId, censored = isCensored)

            when (response.isSuccessful) {
                true -> {
                    val list = response.body()!!.toListAnimePosterEntity()
                    Results.Success(data = list)
                }
                false -> {
                    if (response.code() == 429) {
                        getGenrePosters(genreId, isCensored = isCensored)
                    } else {
                        Results.Error(exception = Exception(response.message()))
                    }
                }
            }

        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }

    override suspend fun getRandomPoster(
        limit: Int,
        censored: Boolean,
        order: String
    ): Results<List<AnimePosterEntity>> {

        return try {
            val response = animeApi.getRandom(censored = censored)

            when (response.isSuccessful) {
                true -> {
                    val list = response.body()!!.toListAnimePosterEntity()

                    Results.Success(data = list)
                }
                false -> {
                    if (response.code() == 429) {
                        getRandomPoster(1, censored, "random")
                    } else
                        Results.Error(exception = Exception(response.message()))
                }
            }

        } catch (e: Exception) {
            Results.Error(exception = e)

        }
    }

    override suspend fun getCharacters(id: Int): Results<CharacterDetailsEntity> {

        return try {
            val response = animeApi.getCharacters(id = id)

            when (response.isSuccessful) {
                true -> {
                    val item: CharacterDetailsEntity = response.body()!!.toCharacterDetailsEntity()
                    Results.Success(data = item)
                }
                false -> {
                    if (response.code() == 429) {
                        getCharacters(id = id)
                    } else {
                        Results.Error(exception = Exception(response.message()))
                    }
                }
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }

    override suspend fun getPersons(id: Int): Results<PersonEntity> {
        return try {
            val response = animeApi.getPersons(id = id)

            when (response.isSuccessful) {
                true -> {
                    val item = response.body()!!.toPersonEntity()
                    Results.Success(data = item)
                }
                false -> {
                    if (response.code() == 429) {
                        getPersons(id = id)
                    } else {
                        Results.Error(exception = Exception(response.message()))
                    }
                }
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }

    override suspend fun addLocalFavorites(item: AnimePosterEntity) {

        shikimoriDAO.insert(item.toLocalListAnimePosterEntity())

    }

    override suspend fun deleteLocalFavorites(id: Int) {
        var isLocal = false
        if (shikimoriDAO.getAllPosters().isNotEmpty()) {
            shikimoriDAO.getAllPosters().forEach {
                if (it.id == id) {
                    isLocal = true
                }
            }
        }

        if (isLocal) {
            shikimoriDAO.delete(id)
        }
    }

    override fun getLocalFavorites(): List<AnimePosterEntity> {
        return shikimoriDAO.getAllPosters().localToListAnimePosterEntity()
    }

    override fun checkIsFavorite(id: Int): Boolean {
        return getLocalFavorites().find { it.id == id } != null
    }
}
