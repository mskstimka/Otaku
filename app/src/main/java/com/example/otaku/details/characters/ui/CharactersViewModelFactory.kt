package com.example.otaku.details.characters.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animator_domain.usecases.*

class CharactersViewModelFactory(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharactersViewModel(
            getCharacterDetailsUseCase = getCharacterDetailsUseCase
        ) as T
    }
}