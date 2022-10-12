package com.example.animator.details.persons.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animator_domain.usecases.GetPersonUseCase

class PersonViewModelFactory(
    private val getPersonUseCase: GetPersonUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PersonViewModel(
            getPersonUseCase = getPersonUseCase
        ) as T
    }
}