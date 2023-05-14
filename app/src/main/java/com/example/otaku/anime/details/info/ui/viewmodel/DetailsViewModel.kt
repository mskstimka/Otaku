package com.example.otaku.anime.details.info.ui.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otaku.anime.details.info.adapters.characters.ContainerCharacters
import com.example.otaku.anime.details.info.adapters.franchises.ContainerFranchises
import com.example.otaku.anime.details.info.adapters.persons.ContainerPerson
import com.example.otaku.anime.details.info.adapters.screenshots.ContainerScreenshots
import com.example.otaku.anime.details.info.adapters.studios.ContainerStudios
import com.example.otaku.anime.details.info.adapters.videos.ContainerVideos
import com.example.otaku.anime.details.info.ui.viewmodel.models.ActionDetailsData
import com.example.otaku.anime.details.info.ui.viewmodel.models.ActionEpisodesData
import com.example.otaku.anime.details.info.ui.viewmodel.models.DetailsTypesData
import com.example.otaku.anime.details.info.ui.viewmodel.models.RolesTypesData
import com.example.otaku.utils.FavoriteAction
import com.example.otaku_data.utils.SharedPreferencesHelper
import com.example.otaku_domain.NO_AUTH_USER_ID
import com.example.otaku_domain.USER_AGENT
import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.details.AnimeDetailsEntity
import com.example.otaku_domain.models.details.Translations
import com.example.otaku_domain.models.user.Type
import com.example.otaku_domain.models.user.status.UserRate
import com.example.otaku_domain.usecases.anime.*
import com.example.otaku_domain.usecases.favorites.CreateFavoriteUseCase
import com.example.otaku_domain.usecases.favorites.DeleteFavoriteUseCase
import com.example.otaku_domain.usecases.user.CreateUserRateUseCase
import com.example.otaku_domain.usecases.user.DeleteRateUseCase
import com.example.otaku_domain.usecases.user.UpdateUserRateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates


class DetailsViewModel @Inject constructor(
    private val getAnimeDetailsFromIdUseCase: GetAnimeDetailsFromIdUseCase,
    private val getAnimeScreenshotsFromIdUseCase: GetAnimeScreenshotsFromIdUseCase,
    private val getAnimeFranchisesFromIdUseCase: GetAnimeFranchisesFromIdUseCase,
    private val getAnimeRolesFromIdUseCase: GetAnimeRolesFromIdUseCase,
    private val getSeriesUseCase: GetSeriesUseCase,
    private val getVideoUseCase: GetVideoUseCase,
    private val updateUserRateUseCase: UpdateUserRateUseCase,
    private val createUserRateUseCase: CreateUserRateUseCase,
    private val deleterRateUseCase: DeleteRateUseCase,
    private val createFavoriteUseCase: CreateFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    private val _actionMessage = MutableSharedFlow<String>(replay = 1)
    val actionMessage: SharedFlow<String> get() = _actionMessage

    private val _lastWatchEpisode = MutableSharedFlow<Int>(replay = 1)
    val lastWatchEpisode: SharedFlow<Int> get() = _lastWatchEpisode

    private val _actionDetails = MutableSharedFlow<ActionDetailsData>(replay = 1)
    val actionDetails: SharedFlow<ActionDetailsData> get() = _actionDetails

    private val _actionScreenshots = MutableSharedFlow<List<ContainerScreenshots>>(replay = 1)
    val actionScreenshots: SharedFlow<List<ContainerScreenshots>> get() = _actionScreenshots

    private val _actionFranchises = MutableSharedFlow<List<ContainerFranchises>>(replay = 1)
    val actionFranchises: SharedFlow<List<ContainerFranchises>> get() = _actionFranchises

    private val _actionRoles = MutableSharedFlow<RolesTypesData>(replay = 1)
    val pageAnimeRolesAction: SharedFlow<RolesTypesData> get() = _actionRoles

    private val _actionProgressBar = MutableSharedFlow<Int>(replay = 1)
    val actionProgressBar: SharedFlow<Int> get() = _actionProgressBar

    private val _actionVideo = MutableSharedFlow<List<Translations>>(replay = 1)
    val actionVideo: SharedFlow<List<Translations>> get() = _actionVideo

    private val _actionEpisodes = MutableSharedFlow<ActionEpisodesData>(replay = 1)
    val actionEpisodes: SharedFlow<ActionEpisodesData> get() = _actionEpisodes

    private val _actionUserRate = MutableSharedFlow<MutableList<AnimeDetailsEntity>>(replay = 1)
    val actionUserRate: SharedFlow<MutableList<AnimeDetailsEntity>> get() = _actionUserRate

    private var currentId by Delegates.notNull<Long>()

    private val resultsFlow = combine(
        _actionDetails,
        _actionScreenshots,
        _actionFranchises,
        _actionRoles
    ) { details, screenshots, franchises, roles ->
        DetailsTypesData(
            details = details,
            screenshots = screenshots,
            franchises = franchises,
            roles = roles
        )
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            resultsFlow.collect {
                _actionProgressBar.tryEmit(View.GONE)
            }
        }
    }

    fun setLastEpisode(episode: Int) {
        viewModelScope.launch {
            _lastWatchEpisode.emit(episode)
        }
    }

    fun actionFavorites(favoriteAction: FavoriteAction) {
        viewModelScope.launch(Dispatchers.IO) {
            when (favoriteAction) {
                is FavoriteAction.CREATE_FAVORITE -> {
                    createFavorite(
                        linkedId = favoriteAction.linkedId,
                        linkedType = favoriteAction.linkedType
                    )
                }
                is FavoriteAction.DELETE_FAVORITE -> {
                    deleteFavorite(
                        linkedId = favoriteAction.linkedId,
                        linkedType = favoriteAction.linkedType
                    )
                }
            }
        }
    }

    private fun deleteFavorite(linkedId: Long, linkedType: Type) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = deleteFavoriteUseCase.execute(
                linkedId = linkedId,
                linkedType = linkedType.typeName
            )) {
                is Results.Success -> {
                    _actionMessage.tryEmit(response.data)
                }
                is Results.Error -> {
                    _actionMessage.tryEmit(response.exception.message.toString())
                }
            }
        }
    }

    private fun createFavorite(linkedId: Long, linkedType: Type) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = createFavoriteUseCase.execute(
                linkedId = linkedId,
                linkedType = linkedType.typeName
            )) {
                is Results.Success -> {
                    _actionMessage.tryEmit(response.data)
                }
                is Results.Error -> {
                    _actionMessage.tryEmit(response.exception.message.toString())
                }
            }
        }
    }

    fun updateOrCreateUserRate(userRate: UserRate) {
        if (userRate.id != null) {
            updateUserRate(userRate)
        } else {
            createUserRate(userRate)
        }
    }

    fun deleteUserRate(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val result = deleterRateUseCase.execute(id = id)) {
                is Results.Success -> {
                    _actionMessage.tryEmit(result.data)
                }
                is Results.Error -> {
                    _actionMessage.tryEmit(result.exception.message.toString())
                }
            }
        }
    }

    private fun createUserRate(userRate: UserRate) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = sharedPreferencesHelper.getCurrentUserId()
            val savedToken = sharedPreferencesHelper.getLocalToken()

            if (userId != NO_AUTH_USER_ID && savedToken != null) {
                when (val response = createUserRateUseCase.execute(
                    userAgent = USER_AGENT,
                    authHeader = "Bearer ${savedToken.authToken}",
                    userRate = userRate,
                    userId = userId
                )) {
                    is Results.Success -> {
                        val details = actionDetails.last().details.first().copy()
                        details.userRate = response.data
                        Log.d("POST", details.toString())
                        _actionUserRate.tryEmit(mutableListOf(details))
                    }
                    is Results.Error -> {
                        _actionMessage.tryEmit(response.exception.message.toString())
                    }
                }
            } else {
                _actionMessage.tryEmit("User/Token Error")
            }

        }
    }

    private fun updateUserRate(userRate: UserRate) {
        viewModelScope.launch(Dispatchers.IO) {

            val newUserRate = userRate
            newUserRate.userId = sharedPreferencesHelper.getCurrentUserId()
            val rateId = userRate.id
            val savedToken = sharedPreferencesHelper.getLocalToken()
            Log.d("User Rate", userRate.toString())

            Log.d("POST", rateId.toString())
            if (rateId != null && savedToken != null) {
                when (val response = updateUserRateUseCase.execute(
                    userAgent = USER_AGENT,
                    authHeader = "Bearer ${savedToken.authToken}",
                    userRate = newUserRate,
                    rateId = rateId
                )) {
                    is Results.Success -> {
                        _actionMessage.tryEmit("User Rate Updated!")
                    }
                    is Results.Error -> {
                        _actionMessage.tryEmit(response.exception.message.toString())
                    }
                }
            } else {
                _actionMessage
            }
        }
    }

    fun getAnimeDetailsFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimeDetailsFromIdUseCase.execute(id = id)) {
                is Results.Success -> {

                    currentId = response.data.id.toLong()
                    Log.d("USER RATE: ", response.data.userRate.toString())
                    _actionDetails.tryEmit(
                        ActionDetailsData(
                            details = mutableListOf(response.data),
                            videos = listOf(ContainerVideos(response.data.videos)),
                            studios = listOf(ContainerStudios(response.data.studios))
                        )
                    )

                    getSeries(
                        malId = response.data.id.toLong(),
                        name = response.data.name.toString(),
                        animeKind = response.data.kind ?: ""
                    )
                }
                is Results.Error -> {
                    _actionMessage.tryEmit(response.exception.message.toString())
                }
            }
        }
    }

    private fun getSeries(
        malId: Long,
        name: String,
        animeKind: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = getSeriesUseCase.execute(malId = malId, name = name)) {
                is Results.Success -> {
                    _actionEpisodes.tryEmit(
                        ActionEpisodesData(
                            episodes = response.data,
                            kind = animeKind,
                            name = name,
                            id = malId
                        )
                    )
                }
                is Results.Error -> {

                }
            }
        }
    }

    fun getVideos(
        malId: Long,
        episode: Int,
        name: String,
        kind: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getVideoUseCase.execute(
                malId = malId,
                episode = episode,
                name = name,
                kind = kind
            )) {
                is Results.Success -> {
                    _actionVideo.tryEmit(response.data)
                }
                is Results.Error -> {
                    Log.d("ERROR", response.exception.message.toString())

                }
            }
        }

    }

    fun getAnimeDetailsScreenshotsFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimeScreenshotsFromIdUseCase.execute(id = id)) {
                is Results.Success -> {
                    _actionScreenshots.tryEmit(
                        listOf(ContainerScreenshots(list = response.data))
                    )
                }
                is Results.Error -> {
                    _actionMessage.tryEmit(response.exception.message.toString())
                }
            }
        }
    }

    fun getAnimeDetailsFranchisesFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimeFranchisesFromIdUseCase.execute(id = id)) {
                is Results.Success -> {
                    _actionFranchises.tryEmit(
                        listOf(
                            ContainerFranchises(list = response.data)
                        )
                    )
                }
                is Results.Error -> {
                    _actionMessage.tryEmit(response.exception.message.toString())
                }
            }
        }
    }

    fun getAnimeDetailsRolesFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = getAnimeRolesFromIdUseCase.execute(id = id)) {
                is Results.Success -> {

                    _actionRoles.tryEmit(
                        RolesTypesData(
                            persons = mutableListOf(
                                ContainerPerson(
                                    list = response.data.person,
                                )
                            ),
                            characters = listOf(ContainerCharacters(list = response.data.character))
                        )
                    )
                }
                is Results.Error -> {
                    _actionMessage.tryEmit(response.exception.message.toString())
                }
            }
        }
    }

}