package com.example.rxjava.details.ui

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.example.a16_rxjava_domain.common.Results
import com.example.a16_rxjava_domain.models.details.AnimeDetailsEntity
import com.example.a16_rxjava_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.a16_rxjava_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.a16_rxjava_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.a16_rxjava_domain.usecases.*
import com.example.rxjava.utils.SingleLiveEvent
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

    private val _pageAnimeRolesAction = MutableLiveData<List<AnimeDetailsRolesEntity>>()
    val pageAnimeRolesAction: LiveData<List<AnimeDetailsRolesEntity>> get() = _pageAnimeRolesAction


    fun getAnimeDetailsFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimeDetailsFromIdUseCase.execute(id = id)) {
                is Results.Success -> {
                    _pageAnimeDetailsAction.postValue(response.data)
                }
                is Results.Error -> _actionError.postValue(response.exception.message)
            }
        }
    }

    fun getAnimeDetailsScreenshotsFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimeScreenshotsFromIdUseCase.execute(id = id)) {
                is Results.Success -> {
                    _pageAnimeScreenshotsAction.postValue(response.data)
                }
                is Results.Error -> _actionError.postValue(response.exception.message)
            }
        }
    }


    fun getAnimeDetailsFranchisesFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimeFranchisesFromIdUseCase.execute(id = id)) {
                is Results.Success -> {
                    _pageAnimeFranchisesAction.postValue(response.data)
                }
                is Results.Error -> _actionError.postValue(response.exception.message)
            }
        }
    }

    fun getAnimeDetailsRolesFromId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = getAnimeRolesFromIdUseCase.execute(id = id)) {
                is Results.Success -> {
                    _pageAnimeRolesAction.postValue(response.data.toMutableList())
                }
                is Results.Error -> _actionError.postValue(response.exception.message)
            }
        }
    }

}