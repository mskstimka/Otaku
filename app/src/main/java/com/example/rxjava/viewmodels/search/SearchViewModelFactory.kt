package com.example.rxjava.viewmodels.search

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a16_rxjava_data.network.RetrofitProvider
import com.example.a16_rxjava_data.repository.AnimeDataSourceImpl
import com.example.a16_rxjava_data.repository.AnimeRepositoryImpl
import com.example.a16_rxjava_domain.usecases.*


class SearchViewModelFactory(
    private val getAnimePostersFromSearchUseCase: GetAnimePostersFromSearchUseCase
) : ViewModelProvider.Factory {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(
            getAnimePostersFromSearchUseCase
        ) as T
    }
}
