package com.example.otaku_data.repository.sources.anime

import com.example.otaku_data.database.dao.ShikimoriDAO
import com.example.otaku_data.mapper.*
import com.example.otaku_data.network.api.AnimeApi
import com.example.otaku_data.utils.SharedPreferencesHelper
import com.example.otaku_data.utils.TranslateUtils
import com.example.otaku_domain.ERROR_WAIT_TIME
import com.example.otaku_domain.USER_AGENT
import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.PersonEntity
import com.example.otaku_domain.models.characters.CharacterDetailsEntity
import com.example.otaku_domain.models.details.AnimeDetailsEntity
import com.example.otaku_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.otaku_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.otaku_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.otaku_domain.models.poster.AnimePosterEntity
import kotlinx.coroutines.delay

class AnimeDataSourceImpl(
    private val animeApi: AnimeApi,
    private val shikimoriDAO: ShikimoriDAO,
    private val sharedPreferencesHelper: SharedPreferencesHelper
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
            val token = sharedPreferencesHelper.getLocalToken()
            val response = if (token != null) {
                animeApi.getDetailsWithAuthorization(
                    id = id,
                    userAgent = USER_AGENT,
                    authHeader = ("Bearer " + token.authToken)
                )
            } else {
                animeApi.getDetails(
                    id = id
                )
            }

            if (response.isSuccessful) {
                val item = response.body()!!.toAnimeDetailsEntity()

                if (sharedPreferencesHelper.getIsUkraineLanguage()) {
                    Results.Success(
                        data = TranslateUtils.translateAnimeDetailsToUkraine(
                            item,
                            isDescriptionTranslate = sharedPreferencesHelper.getIsUkraineDescription(),
                            isNameTranslate = sharedPreferencesHelper.getIsUkraineName()
                        )
                    )
                } else {
                    Results.Success(data = item)
                }
            } else {
                if (response.code() == 429) {
                    delay(ERROR_WAIT_TIME)
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
                    delay(ERROR_WAIT_TIME)
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
                    delay(ERROR_WAIT_TIME)
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
                    delay(ERROR_WAIT_TIME)
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
                        delay(ERROR_WAIT_TIME)
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
                        delay(ERROR_WAIT_TIME)
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
                    val item = response.body()!!.toCharacterDetailsEntity()
                    if (sharedPreferencesHelper.getIsUkraineLanguage()) {
                        Results.Success(
                            data = TranslateUtils.translateCharacterToUkraine(
                                item,
                                isDescriptionTranslate = sharedPreferencesHelper.getIsUkraineDescription(),
                                isNameTranslate = sharedPreferencesHelper.getIsUkraineName()
                            )
                        )
                    } else {
                        Results.Success(data = item)
                    }
                }
                false -> {
                    if (response.code() == 429) {
                        delay(ERROR_WAIT_TIME)
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
                    if (sharedPreferencesHelper.getIsUkraineLanguage()) {
                        Results.Success(
                            data = TranslateUtils.translatePersonToUkraine(
                                item,
                                isDescriptionTranslate = sharedPreferencesHelper.getIsUkraineDescription(),
                                isNameTranslate = sharedPreferencesHelper.getIsUkraineName()
                            )
                        )
                    } else {
                        Results.Success(data = item)
                    }
                }
                false -> {
                    if (response.code() == 429) {
                        delay(ERROR_WAIT_TIME)
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
