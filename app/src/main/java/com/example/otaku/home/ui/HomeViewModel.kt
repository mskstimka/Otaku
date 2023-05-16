package com.example.otaku.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otaku.R
import com.example.otaku.home.adapters.genres.ContainerGenresList
import com.example.otaku.home.adapters.random.ContainerRandom
import com.example.otaku_data.utils.SharedPreferencesHelper
import com.example.otaku_domain.*
import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.otaku_domain.models.home.PrevPoster
import com.example.otaku_domain.models.poster.AnimePosterEntity
import com.example.otaku_domain.models.user.status.RateStatus
import com.example.otaku_domain.usecases.anime.GetAnimePrevPosterFromGenreUseCase
import com.example.otaku_domain.usecases.anime.GetAnimeRandomPosterUseCase
import com.example.otaku_domain.usecases.anime.GetAnimeScreenshotsFromIdUseCase
import com.example.otaku_domain.usecases.user.GetUserWatchingAnimeRatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getAnimePrevPosterFromGenreUseCase: GetAnimePrevPosterFromGenreUseCase,
    private val getAnimeRandomPosterUseCase: GetAnimeRandomPosterUseCase,
    private val getAnimeScreenshotsFromIdUseCase: GetAnimeScreenshotsFromIdUseCase,
    private val getUserWatchingAnimeRatesUseCase: GetUserWatchingAnimeRatesUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    val arrayPrefPosters = listOf(
        PrevPoster(genreId = ROMANTIC_ID, genreName = R.string.romantic_title),
        PrevPoster(genreId = SHOUNEN_ID, genreName = R.string.shounen_title),
        PrevPoster(genreId = DRAMA_ID, genreName = R.string.drama_title),
        PrevPoster(genreId = DEMONS_ID, genreName = R.string.demon_title),
        PrevPoster(genreId = SHOUJO_ID, genreName = R.string.shoujo_title),
        PrevPoster(genreId = HAREM_ID, genreName = R.string.harem_title)
    )

    private val _actionMessage = MutableSharedFlow<String>(replay = 1)
    val actionMessage: SharedFlow<String> = _actionMessage

    private val _actionAnimePosters = MutableSharedFlow<List<ContainerGenresList>>(replay = 1)
    val actionAnimePosters: SharedFlow<List<ContainerGenresList>> = _actionAnimePosters

    private val _actionAnimeRandom = MutableSharedFlow<List<ContainerRandom>>(replay = 1)
    val actionAnimeRandom: SharedFlow<List<ContainerRandom>> = _actionAnimeRandom

    private val _actionAnimeScreenshots =
        MutableSharedFlow<List<AnimeDetailsScreenshotsEntity>>(replay = 1)
    val actionAnimeScreenshots: SharedFlow<List<AnimeDetailsScreenshotsEntity>> get() = _actionAnimeScreenshots

    private val _actionFavorites =
        MutableSharedFlow<List<AnimePosterEntity>>(replay = 1)
    val actionFavorites: SharedFlow<List<AnimePosterEntity>> get() = _actionFavorites


    val list = mutableListOf<ContainerGenresList>().also {
        refresh(arrayPrefPosters)
    }


    fun refresh(list: List<PrevPoster>) {

        getList(
            list
        )
        getAnimeRandomPoster()
        getFavoritePosters()
    }


    private fun getFavoritePosters() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentId = sharedPreferencesHelper.getCurrentUserId()

            if (currentId != NO_CURRENT_USER_ID) {
                val result = getUserWatchingAnimeRatesUseCase.execute(
                    id = currentId,
                    status = RateStatus.WATCHING.status,
                    page = 1,
                    limit = 20
                )

                when (result) {
                    is Results.Success -> {
                        _actionFavorites.tryEmit(result.data.mapNotNull { it.anime })
                    }
                    is Results.Error -> {
                        _actionMessage.tryEmit(result.exception.message.toString())
                    }
                }
            }
        }
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
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = getAnimeRandomPosterUseCase.execute()) {
                is Results.Success -> {
                    _actionAnimeRandom.tryEmit(
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
                    _actionMessage.tryEmit(response.exception.message.toString())
                }
            }
        }
    }

    private fun getAnimeDetailsScreenshotsFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimeScreenshotsFromIdUseCase.execute(id = id)) {
                is Results.Success -> {
                    if (response.data.first().original == NOT_FOUND_TEXT) {
                        _actionAnimeScreenshots.tryEmit(emptyList())
                    } else {
                        _actionAnimeScreenshots.tryEmit(response.data)
                    }
                }
                is Results.Error -> {
                    _actionMessage.tryEmit(response.exception.message.toString())
                }
            }
        }
    }


    private fun getAnimePrevPosterActionFromGenre(genresId: Int, genreName: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimePrevPosterFromGenreUseCase.execute(genresId)) {
                is Results.Success -> {
                    list.add(ContainerGenresList(title = genreName, list = response.data))
                    if (list.size == arrayPrefPosters.size) {
                        _actionAnimePosters.tryEmit(list)
                    }
                }
                is Results.Error -> {
                    _actionMessage.tryEmit(response.exception.message.toString())
                }
            }
        }
    }

    companion object {
        const val CONTAINER_RANDOM_ID = 1
    }
}

