package com.example.otaku.details.info.ui

import android.util.Log
import android.widget.ProgressBar
import androidx.core.text.parseAsHtml
import androidx.lifecycle.*
import com.example.otaku.details.info.adapters.franchises.ContainerFranchises
import com.example.otaku.details.info.adapters.screenshots.ContainerScreenshots
import com.example.animator_domain.common.Results
import com.example.animator_domain.models.details.AnimeDetailsEntity
import com.example.animator_domain.models.details.Translations
import com.example.animator_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.animator_domain.models.poster.AnimePosterEntity
import com.example.animator_domain.usecases.*
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.util.*


class DetailsViewModel(
    private val getAnimeDetailsFromIdUseCase: GetAnimeDetailsFromIdUseCase,
    private val getAnimeScreenshotsFromIdUseCase: GetAnimeScreenshotsFromIdUseCase,
    private val getAnimeFranchisesFromIdUseCase: GetAnimeFranchisesFromIdUseCase,
    private val getAnimeRolesFromIdUseCase: GetAnimeRolesFromIdUseCase,
    private val getSeriesUseCase: GetSeriesUseCase,
    private val getVideoUseCase: GetVideoUseCase,
    private val deleteFavoritesUseCase: DeleteFavoritesUseCase,
    private val addFavoritesUseCase: AddFavoritesUseCase,
    private val checkIsFavoriteUseCase: CheckIsFavoriteUseCase
) : ViewModel() {


    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> get() = _actionError

    private val _pageAnimeDetailsAction = MutableSharedFlow<AnimeDetailsEntity>(replay = 1)
    val pageAnimeDetailsAction: SharedFlow<AnimeDetailsEntity> get() = _pageAnimeDetailsAction

    private val _pageAnimeScreenshotsAction =
        MutableSharedFlow<ContainerScreenshots>(replay = 1)
    val pageAnimeScreenshotsAction: SharedFlow<ContainerScreenshots> get() = _pageAnimeScreenshotsAction

    private val _pageAnimeFranchisesAction =
        MutableSharedFlow<ContainerFranchises>(replay = 1)
    val pageAnimeFranchisesAction: SharedFlow<ContainerFranchises> get() = _pageAnimeFranchisesAction

    private val _pageAnimeRolesAction = MutableSharedFlow<AnimeDetailsRolesEntity>(replay = 1)
    val pageAnimeRolesAction: SharedFlow<AnimeDetailsRolesEntity> get() = _pageAnimeRolesAction

    private val responses = mutableListOf<Boolean>()

    private val _actionAdapter = MutableSharedFlow<Int>(replay = 1)
    val actionAdapter: SharedFlow<Int> get() = _actionAdapter

    private val _actionVideo = MutableSharedFlow<List<Translations>>(replay = 0)
    val actionVideo: SharedFlow<List<Translations>> get() = _actionVideo


    private val _actionEpisodes = MutableSharedFlow<Int>(replay = 1)
    val actionEpisodes: SharedFlow<Int> get() = _actionEpisodes

    private suspend fun putResponses(value: Boolean) {
        responses.add(value)

        if (Locale.getDefault().language != "uk") {
            if (responses.count() >= 4) {
                _actionAdapter.emit(ProgressBar.INVISIBLE)
            }
        } else {
            if (responses.count() >= 6) {
                _actionAdapter.emit(ProgressBar.INVISIBLE)
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

    fun getAnimeDetailsFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimeDetailsFromIdUseCase.execute(id = id)) {
                is Results.Success -> {
                    _pageAnimeDetailsAction.emit(response.data)
                    if (Locale.getDefault().language == "uk") {
                        translateToUkraine(response.data)
                    }
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

    private fun translateToUkraine(item: AnimeDetailsEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            var items = AnimeDetailsEntity(
                aired_on = item.aired_on,
                description = item.description,
                description_html = item.description_html,
                episodes = item.episodes,
                episodes_aired = item.episodes_aired,
                genres = item.genres,
                id = item.id,
                image = item.image,
                kind = item.kind,
                name = item.name,
                russian = item.russian,
                score = item.score,
                screenshots = item.screenshots,
                status = item.status,
                statusColor = item.statusColor,
                studios = item.studios,
                videos = item.videos
            )

            var conditions = DownloadConditions.Builder()
                .requireWifi()
                .build()

            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.RUSSIAN)
                .setTargetLanguage(TranslateLanguage.UKRAINIAN)
                .build()
            val translator = Translation.getClient(options)

            translator.downloadModelIfNeeded(conditions).addOnSuccessListener {
                translator.translate(item.description_html.parseAsHtml().toString())
                    .addOnSuccessListener {

                        viewModelScope.launch {
                            items.description_html = it
                            putResponses(true)
                            _pageAnimeDetailsAction.emit(items)
                        }
                    }
                translator.translate(item.russian!!)
                    .addOnSuccessListener {

                        viewModelScope.launch {
                            putResponses(true)
                            items.russian = it
                            _pageAnimeDetailsAction.emit(items)
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
                    _pageAnimeScreenshotsAction.emit(
                        ContainerScreenshots(list = response.data)
                    )
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
                    _pageAnimeFranchisesAction.emit(
                        ContainerFranchises(list = response.data)
                    )
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