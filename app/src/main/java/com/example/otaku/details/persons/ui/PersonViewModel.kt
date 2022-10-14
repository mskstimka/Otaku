package com.example.otaku.details.persons.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animator_domain.common.Results
import com.example.animator_domain.models.PersonEntity
import com.example.animator_domain.usecases.GetPersonUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class PersonViewModel(
    private val getPersonUseCase: GetPersonUseCase
) : ViewModel() {

    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> get() = _actionError

    private val _actionInfo = MutableSharedFlow<PersonEntity>(replay = 1)
    val actionInfo: SharedFlow<PersonEntity> get() = _actionInfo


    fun getPerson(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getPersonUseCase.execute(id = id)) {
                is Results.Success -> {
                    _actionInfo.emit(response.data)
                }
                is Results.Error -> {
                    _actionError.emit(response.exception.message.toString())

                }
            }
        }
    }

}