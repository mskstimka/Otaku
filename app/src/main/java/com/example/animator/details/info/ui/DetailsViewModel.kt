package com.example.animator.details.info.ui

import android.util.Log
import android.widget.ProgressBar
import androidx.lifecycle.*
import com.example.animator_domain.common.Results
import com.example.animator_domain.models.details.AnimeDetailsEntity
import com.example.animator_domain.models.details.Translation
import com.example.animator_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.animator_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.animator_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.animator_domain.usecases.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow


class DetailsViewModel(
    private val getAnimeDetailsFromIdUseCase: GetAnimeDetailsFromIdUseCase,
    private val getAnimeScreenshotsFromIdUseCase: GetAnimeScreenshotsFromIdUseCase,
    private val getAnimeFranchisesFromIdUseCase: GetAnimeFranchisesFromIdUseCase,
    private val getAnimeRolesFromIdUseCase: GetAnimeRolesFromIdUseCase,
    private val getSeriesUseCase: GetSeriesUseCase,
    private val getVideoUseCase: GetVideoUseCase
) : ViewModel() {


    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> get() = _actionError

    private val _pageAnimeDetailsAction = MutableSharedFlow<AnimeDetailsEntity>(replay = 1)
    val pageAnimeDetailsAction: SharedFlow<AnimeDetailsEntity> get() = _pageAnimeDetailsAction

    private val _pageAnimeScreenshotsAction =
        MutableSharedFlow<List<AnimeDetailsScreenshotsEntity>>(replay = 1)
    val pageAnimeScreenshotsAction: SharedFlow<List<AnimeDetailsScreenshotsEntity>> get() = _pageAnimeScreenshotsAction

    private val _pageAnimeFranchisesAction =
        MutableSharedFlow<List<AnimeDetailsFranchisesEntity>>(replay = 1)
    val pageAnimeFranchisesAction: SharedFlow<List<AnimeDetailsFranchisesEntity>> get() = _pageAnimeFranchisesAction

    private val _pageAnimeRolesAction = MutableSharedFlow<AnimeDetailsRolesEntity>(replay = 1)
    val pageAnimeRolesAction: SharedFlow<AnimeDetailsRolesEntity> get() = _pageAnimeRolesAction

    private val responses = mutableListOf<Boolean>()

    private val _actionAdapter = MutableSharedFlow<Int>(replay = 1)
    val actionAdapter: SharedFlow<Int> get() = _actionAdapter

    private val _actionVideo = MutableSharedFlow<List<Translation>>(replay = 0)
    val actionVideo: SharedFlow<List<Translation>> get() = _actionVideo


    private val _actionEpisodes = MutableSharedFlow<Int>(replay = 1)
    val actionEpisodes: SharedFlow<Int> get() = _actionEpisodes

    private suspend fun putResponses(value: Boolean) {
        responses.add(value)

        if (responses.count() >= 4) {
            _actionAdapter.emit(ProgressBar.INVISIBLE)
        }
    }

    fun getAnimeDetailsFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimeDetailsFromIdUseCase.execute(id = id)) {
                is Results.Success -> {
                    _pageAnimeDetailsAction.emit(response.data)
                    getSeries(response.data.id.toLong(), response.data.name.toString())
                    putResponses(true)
                }
                is Results.Error -> {
                    _actionError.emit(response.exception.message.toString())
                    putResponses(false)
                }
            }
        }
    }

    private fun getSeries(malId: Long, name: String) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getSeriesUseCase.execute(malId = malId, name = name)) {
                is Results.Success -> {
                    _actionEpisodes.emit(response.data)
                }
                is Results.Error -> {
                    _actionError.emit(response.exception.message.toString())
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
                    _actionVideo.emit(response.data)
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
                    _pageAnimeScreenshotsAction.emit(response.data)
                    putResponses(true)
                }
                is Results.Error -> {
                    _actionError.emit(response.exception.message.toString())
                    putResponses(false)
                }
            }
        }
    }

    fun getAnimeDetailsFranchisesFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimeFranchisesFromIdUseCase.execute(id = id)) {
                is Results.Success -> {
                    _pageAnimeFranchisesAction.emit(response.data)
                    putResponses(true)
                }
                is Results.Error -> {
                    _actionError.emit(response.exception.message.toString())
                    putResponses(false)
                }
            }
        }
    }

    fun getAnimeDetailsRolesFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = getAnimeRolesFromIdUseCase.execute(id = id)) {
                is Results.Success -> {
                    _pageAnimeRolesAction.emit(response.data)
                    putResponses(true)
                }
                is Results.Error -> {
                    _actionError.emit(response.exception.message.toString())
                    putResponses(false)
                }
            }
        }
    }

}