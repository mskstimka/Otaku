package com.example.otaku.anime.details.info.ui.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Results
import com.example.domain.models.details.Translations
import com.example.domain.models.poster.AnimePosterEntity
import com.example.domain.usecases.anime.*
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class DetailsViewModel @Inject constructor(
    private val getAnimeDetailsFromIdUseCase: GetAnimeDetailsFromIdUseCase,
    private val getAnimeScreenshotsFromIdUseCase: GetAnimeScreenshotsFromIdUseCase,
    private val getAnimeFranchisesFromIdUseCase: GetAnimeFranchisesFromIdUseCase,
    private val getAnimeRolesFromIdUseCase: GetAnimeRolesFromIdUseCase,
    private val getSeriesUseCase: GetSeriesUseCase,
    private val getVideoUseCase: GetVideoUseCase,
    private val deleteFavoritesUseCase: DeleteFavoritesUseCase,
    private val addFavoritesUseCase: AddFavoritesUseCase,
    private val checkIsFavoriteUseCase: CheckIsFavoriteUseCase,
) : ViewModel() {

    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> get() = _actionError

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

    fun addFavoritesId(item: AnimePosterEntity) =
        viewModelScope.launch(Dispatchers.IO) { addFavoritesUseCase.execute(item) }

    fun deleteFavoritesId(id: Int) =
        viewModelScope.launch(Dispatchers.IO) { deleteFavoritesUseCase.execute(id) }

    fun checkIsFavorite(id: Int): Boolean {
        return runBlocking(Dispatchers.IO) {
            return@runBlocking checkIsFavoriteUseCase.execute(id = id)
        }
    }

    fun setLastEpisode(episode: Int) {
        viewModelScope.launch {
            _lastWatchEpisode.emit(episode)
        }
    }

    fun getAnimeDetailsFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimeDetailsFromIdUseCase.execute(id = id)) {
                is Results.Success -> {

                    _actionDetails.tryEmit(
                        ActionDetailsData(
                            details = listOf(response.data),
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
                    _actionError.emit(response.exception.message.toString())
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
                    _actionError.tryEmit(response.exception.message.toString())
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
                    _actionError.tryEmit(response.exception.message.toString())
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
                    _actionError.tryEmit(response.exception.message.toString())
                }
            }
        }
    }

}