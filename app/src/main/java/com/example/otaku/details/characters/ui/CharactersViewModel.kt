package com.example.otaku.details.characters.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animator_domain.common.Results
import com.example.animator_domain.models.characters.CharacterDetailsEntity
import com.example.animator_domain.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase
) : ViewModel() {

    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> get() = _actionError

    private val _actionInfo = MutableSharedFlow<CharacterDetailsEntity>(replay = 1)
    val actionInfo: SharedFlow<CharacterDetailsEntity> get() = _actionInfo


    fun getCharacters(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getCharacterDetailsUseCase.execute(id = id)) {
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

