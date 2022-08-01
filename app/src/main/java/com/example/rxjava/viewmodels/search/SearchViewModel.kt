package com.example.rxjava.viewmodels.search

import android.app.Application
import androidx.lifecycle.*
import com.example.a16_rxjava_domain.common.Results
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import com.example.a16_rxjava_domain.usecases.GetAnimeDetailsFromIdUseCase
import com.example.a16_rxjava_domain.usecases.GetAnimePostersFromSearchUseCase
import com.example.rxjava.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getAnimePostersFromSearchUseCase: GetAnimePostersFromSearchUseCase
) : ViewModel() {
    private val _listAnimePosterAction = MutableLiveData<List<AnimePosterEntity>>()
    val listAnimePosterAction: LiveData<List<AnimePosterEntity>> get() = _listAnimePosterAction

    private val _actionError = SingleLiveEvent<String>()
    val actionError: LiveData<String> get() = _actionError

    private val disposable = CompositeDisposable()

    fun getAnimePostersFromSearch(searchName: String) {

        if (searchName != "") {
            disposable.add(
                getAnimePostersFromSearchUseCase
                    .execute(searchName)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        _listAnimePosterAction.postValue(it)
                    }, { throwable ->
                        _actionError.postValue(throwable.message)
                    })
            )
        } else {
            _listAnimePosterAction.postValue(emptyList())

        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}