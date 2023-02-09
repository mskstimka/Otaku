package com.example.otaku.anime.details.characters.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animator_data.utils.SharedPreferencesHelper
import com.example.domain.usecases.*

class CharactersViewModelFactory(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharactersViewModel(
            getCharacterDetailsUseCase = getCharacterDetailsUseCase,
            sharedPreferencesHelper = sharedPreferencesHelper
        ) as T
    }
}