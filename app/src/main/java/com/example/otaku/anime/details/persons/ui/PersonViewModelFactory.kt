package com.example.otaku.anime.details.persons.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animator_data.utils.SharedPreferencesHelper
import com.example.animator_domain.usecases.GetPersonUseCase

class PersonViewModelFactory(
    private val getPersonUseCase: GetPersonUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PersonViewModel(
            getPersonUseCase = getPersonUseCase,
            sharedPreferencesHelper = sharedPreferencesHelper
        ) as T
    }
}