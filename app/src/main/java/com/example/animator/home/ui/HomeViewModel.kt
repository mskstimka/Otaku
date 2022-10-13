package com.example.animator.home.ui

import android.util.Log
import androidx.lifecycle.*
import androidx.work.impl.model.Preference
import com.example.animator.home.adapters.genres.ContainerGenresList
import com.example.animator.home.adapters.random.ContainerRandom
import com.example.animator.utils.CountUtls
import com.example.animator_domain.*
import com.example.animator_domain.common.Results
import com.example.animator_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.animator_domain.usecases.*
import com.example.animator_domain.models.home.PrevPoster
import com.example.animator_domain.models.poster.AnimePosterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAnimePrevPosterFromGenreUseCase: GetAnimePrevPosterFromGenreUseCase,
    private val getAnimeRandomPosterUseCase: GetAnimeRandomPosterUseCase,
    private val getAnimeScreenshotsFromIdUseCase: GetAnimeScreenshotsFromIdUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    val responses = mutableListOf<Boolean>()

    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> = _actionError

    private val _pageAnimePosterAction = MutableSharedFlow<List<ContainerGenresList>>(replay = 1)
    val pageAnimePosterAction: SharedFlow<List<ContainerGenresList>> = _pageAnimePosterAction

    private val _pageAnimeRandomAction = MutableSharedFlow<List<ContainerRandom>>(replay = 1)
    val pageAnimeRandomAction: SharedFlow<List<ContainerRandom>> = _pageAnimeRandomAction

    private val _pageAnimeScreenshotsAction =
        MutableSharedFlow<List<AnimeDetailsScreenshotsEntity>>(replay = 1)
    val pageAnimeScreenshotsAction: SharedFlow<List<AnimeDetailsScreenshotsEntity>> get() = _pageAnimeScreenshotsAction

    private val _actionFavorites =
        MutableSharedFlow<List<AnimePosterEntity>>(replay = 1)
    val actionFavorites: SharedFlow<List<AnimePosterEntity>> get() = _actionFavorites


    val list = mutableListOf<ContainerGenresList>().also {
        refresh(ARRAY_PREV_POSTERS)
    }

    fun refresh(list: List<PrevPoster>) {

        getList(
            list
        )
        getAnimeRandomPoster()
        getFavoritePosters()
    }


    private fun getFavoritePosters() = viewModelScope.launch(Dispatchers.IO) {
        _actionFavorites.emit(getFavoritesUseCase.execute())
    }

    private fun getList(list: List<PrevPoster>) {
        viewModelScope.launch {
            list.forEach { item ->
                getAnimePrevPosterActionFromGenre(
                    genresId = item.genreId,
                    genreName = item.genreName
                )
            }
        }
    }

    fun getAnimeRandomPoster() {
        viewModelScope.launch {
            when (val response = getAnimeRandomPosterUseCase.execute()) {
                is Results.Success -> {
                    _pageAnimeRandomAction.emit(
                        listOf(
                            ContainerRandom(
                                id = CONTAINER_RANDOM_ID,
                                item = response.data.first()
                            )
                        )
                    )
                    getAnimeDetailsScreenshotsFromId(response.data.first().id)
                }
                is Results.Error -> {
                    _actionError.emit(response.exception.message.toString())
                }
            }
        }
    }

    private fun getAnimeDetailsScreenshotsFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimeScreenshotsFromIdUseCase.execute(id = id)) {
                is Results.Success -> {
                    if (response.data.first().original == NOT_FOUND_TEXT) {
                        _pageAnimeScreenshotsAction.emit(emptyList())
                    } else {
                        _pageAnimeScreenshotsAction.emit(response.data)
                    }
                }
                is Results.Error -> {
                    _actionError.emit(response.exception.message.toString())
                }
            }
        }
    }

    private suspend fun putResponses(value: Boolean) = viewModelScope.launch {
        responses.add(value)

        if (responses.count() >= 6) {
            _pageAnimePosterAction.emit(list)
        }
    }

    private fun getAnimePrevPosterActionFromGenre(genresId: Int, genreName: String) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimePrevPosterFromGenreUseCase.execute(genresId)) {
                is Results.Success -> {
                    list.add(ContainerGenresList(title = genreName, list = response.data))
                    putResponses(true)
                }
                is Results.Error -> {
                    putResponses(false)
                    _actionError.emit(response.exception.message.toString())
                }
            }
        }
    }

    companion object {
        const val CONTAINER_RANDOM_ID = 1
        const val CONTAINER_GENRES_ID = 2
    }
}

