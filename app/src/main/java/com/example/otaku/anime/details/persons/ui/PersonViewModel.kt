package com.example.otaku.anime.details.persons.ui

import android.widget.ProgressBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animator_data.utils.SharedPreferencesHelper
import com.example.domain.common.Results
import com.example.domain.models.PersonEntity
import com.example.domain.usecases.GetPersonUseCase
import com.example.otaku.utils.TranslateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PersonViewModel @Inject constructor(
    private val getPersonUseCase: GetPersonUseCase,
    sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> get() = _actionError

    private val _actionInfo = MutableSharedFlow<PersonEntity>(replay = 1)
    val actionInfo: SharedFlow<PersonEntity> get() = _actionInfo

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

    private fun translateToUkraine(item: PersonEntity) {
        viewModelScope.launch(Dispatchers.IO) {

            val localItem: PersonEntity = item.copy()
            TranslateUtils.translatePersonToUkraine(
                item = item,
                isDescriptionTranslate = isDescriptionTranslate,
                isNameTranslate = isNameTranslate,
                actionDescription = {
                    viewModelScope.launch {
                        localItem.jobTitle = it
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


    fun getPerson(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getPersonUseCase.execute(id = id)) {
                is Results.Success -> {
                    if (isUkraine) {
                        translateToUkraine(response.data)
                    }
                    putResponses(true)
                    _actionInfo.emit(response.data)
                }
                is Results.Error -> {
                    _actionError.emit(response.exception.message.toString())
                    putResponses(false)
                }
            }
        }
    }

}