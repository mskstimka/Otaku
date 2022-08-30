package com.example.animator.details.ui

import android.annotation.SuppressLint
import android.widget.ProgressBar
import androidx.lifecycle.*
import com.example.animator_domain.common.Results
import com.example.animator_domain.models.details.AnimeDetailsEntity
import com.example.animator_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.animator_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.animator_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.animator_domain.usecases.*
import com.example.animator.utils.SingleLiveEvent
import kotlinx.coroutines.*

@SuppressLint("NullSafeMutableLiveData")
class DetailsViewModel(
    private val getAnimeDetailsFromIdUseCase: GetAnimeDetailsFromIdUseCase,
    private val getAnimeScreenshotsFromIdUseCase: GetAnimeScreenshotsFromIdUseCase,
    private val getAnimeFranchisesFromIdUseCase: GetAnimeFranchisesFromIdUseCase,
    private val getAnimeRolesFromIdUseCase: GetAnimeRolesFromIdUseCase
) : ViewModel() {


    private val _actionError = SingleLiveEvent<String>()
    val actionError: LiveData<String> get() = _actionError

    private val _pageAnimeDetailsAction = MutableLiveData<AnimeDetailsEntity>()
    val pageAnimeDetailsAction: LiveData<AnimeDetailsEntity> get() = _pageAnimeDetailsAction

    private val _pageAnimeScreenshotsAction = MutableLiveData<List<AnimeDetailsScreenshotsEntity>>()
    val pageAnimeScreenshotsAction: LiveData<List<AnimeDetailsScreenshotsEntity>> get() = _pageAnimeScreenshotsAction

    private val _pageAnimeFranchisesAction = MutableLiveData<List<AnimeDetailsFranchisesEntity>>()
    val pageAnimeFranchisesAction: LiveData<List<AnimeDetailsFranchisesEntity>> get() = _pageAnimeFranchisesAction

    private val _pageAnimeRolesAction = MutableLiveData<AnimeDetailsRolesEntity>()
    val pageAnimeRolesAction: LiveData<AnimeDetailsRolesEntity> get() = _pageAnimeRolesAction

    private val responses = mutableListOf<Boolean>()

    private val _actionAdapter = MutableLiveData<Int>()
    val actionAdapter: LiveData<Int> get() = _actionAdapter


    private fun putResponses(value: Boolean) {
        responses.add(value)

        if (responses.count() == 4) {
            _actionAdapter.postValue(ProgressBar.INVISIBLE)
        }
    }

    fun getAnimeDetailsFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimeDetailsFromIdUseCase.execute(id = id)) {
                is Results.Success -> {
                    _pageAnimeDetailsAction.postValue(response.data)
                    putResponses(true)
                }
                is Results.Error -> {
                    _actionError.postValue(response.exception.message)
                    putResponses(false)
                }
            }
        }
    }

    fun getAnimeDetailsScreenshotsFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimeScreenshotsFromIdUseCase.execute(id = id)) {
                is Results.Success -> {
                    _pageAnimeScreenshotsAction.postValue(response.data)
                    putResponses(true)
                }
                is Results.Error -> {
                    _actionError.postValue(response.exception.message)
                    putResponses(false)
                }
            }
        }
    }


    fun getAnimeDetailsFranchisesFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimeFranchisesFromIdUseCase.execute(id = id)) {
                is Results.Success -> {
                    _pageAnimeFranchisesAction.postValue(response.data)
                    putResponses(true)
                }
                is Results.Error -> {
                    _actionError.postValue(response.exception.message)
                    putResponses(false)
                }
            }
        }
    }

    fun getAnimeDetailsRolesFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = getAnimeRolesFromIdUseCase.execute(id = id)) {
                is Results.Success -> {
                    _pageAnimeRolesAction.postValue(response.data)
                    putResponses(true)
                }
                is Results.Error -> {
                    _actionError.postValue(response.exception.message)
                    putResponses(false)
                }
            }
        }
    }

}