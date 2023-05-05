package com.example.otaku.anime.details.characters.ui

import android.widget.ProgressBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animator_data.utils.SharedPreferencesHelper
import com.example.domain.common.Results
import com.example.domain.models.characters.CharacterDetailsEntity
import com.example.domain.usecases.anime.GetCharacterDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> get() = _actionError

    private val _actionInfo = MutableSharedFlow<CharacterDetailsEntity>(replay = 1)
    val actionInfo: SharedFlow<CharacterDetailsEntity> get() = _actionInfo

    private val _actionAdapter = MutableSharedFlow<Int>(replay = 1)
    val actionAdapter: SharedFlow<Int> get() = _actionAdapter

    private val responses = mutableListOf<Boolean>()

    private suspend fun putResponses(value: Boolean) {
        responses.add(value)
        val responseCount =
            if (!isUkraine) 1 else if (isDescriptionTranslate && isNameTranslate) 3 else if (!isDescriptionTranslate && !isNameTranslate) 1 else 2

        if (responses.count() >= responseCount) {
            _actionAdapter.emit(ProgressBar.INVISIBLE)
        }
    }

    private val isUkraine = sharedPreferencesHelper.getIsUkraineLanguage()
    private val isNameTranslate = sharedPreferencesHelper.getIsUkraineName()
    private val isDescriptionTranslate = sharedPreferencesHelper.getIsUkraineDescription()



    fun getCharacters(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getCharacterDetailsUseCase.execute(id = id)) {
                is Results.Success -> {
                    _actionInfo.emit(response.data)
                    putResponses(true)
                }
                is Results.Error -> {
                    _actionError.emit(response.exception.message.toString())
                    putResponses(false)
                }
            }
        }
    }

}

