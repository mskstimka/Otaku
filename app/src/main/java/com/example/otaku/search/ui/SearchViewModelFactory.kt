package com.example.otaku.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animator_domain.usecases.GetAnimePostersFromSearchUseCase

class SearchViewModelFactory(
    private val getAnimePostersFromSearchUseCase: GetAnimePostersFromSearchUseCase
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(
            getAnimePostersFromSearchUseCase = getAnimePostersFromSearchUseCase
        ) as T
    }
}
