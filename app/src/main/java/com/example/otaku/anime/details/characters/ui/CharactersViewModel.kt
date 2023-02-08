package com.example.otaku.anime.details.characters.ui

import android.widget.ProgressBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animator_data.utils.SharedPreferencesHelper
import com.example.animator_domain.common.Results
import com.example.animator_domain.models.characters.CharacterDetailsEntity
import com.example.animator_domain.models.details.AnimeDetailsEntity
import com.example.animator_domain.usecases.*
import com.example.otaku.utils.TranslateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper
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

    private fun translateToUkraine(item: CharacterDetailsEntity) {
        viewModelScope.launch(Dispatchers.IO) {

            val localItem: CharacterDetailsEntity = item.copy()
            TranslateUtils.translateCharacterToUkraine(
                item = item,
                isDescriptionTranslate = isDescriptionTranslate,
                isNameTranslate = isNameTranslate,
                actionDescription = {
                    viewModelScope.launch {
                        localItem.description_html = it
                        putResponses(true)
                        _actionInfo.emit(localItem)
                    }
                },
                actionName = {
                    viewModelScope.launch {
                        putResponses(true)
                        localItem.nameRu = it
                        _actionInfo.emit(localItem)
                    }
                })
        }
    }

    fun getCharacters(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getCharacterDetailsUseCase.execute(id = id)) {
                is Results.Success -> {
                    if (isUkraine) {
                        translateToUkraine(response.data)
                    }
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

