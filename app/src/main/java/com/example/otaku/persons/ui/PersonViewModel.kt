package com.example.otaku.persons.ui

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otaku_data.utils.SharedPreferencesHelper
import com.example.otaku_domain.common.Results
import com.example.otaku_domain.usecases.anime.GetPersonUseCase
import com.example.otaku.anime.details.info.adapters.characters.ContainerCharacters
import com.example.otaku.persons.adapters.info.ContainerPersonInfo
import com.example.otaku.persons.adapters.roles.ContainerWorks
import com.example.otaku.persons.ui.models.ActionPersonData
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

    private val _actionInfo = MutableSharedFlow<ActionPersonData>(replay = 1)
    val actionInfo: SharedFlow<ActionPersonData> get() = _actionInfo

    private val _actionProgressBar = MutableSharedFlow<Int>(replay = 1)
    val actionProgressBar: SharedFlow<Int> get() = _actionProgressBar

    fun getPerson(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = getPersonUseCase.execute(id = id)) {
                is Results.Success -> {
                    _actionInfo.tryEmit(
                        ActionPersonData(
                            person = listOf(
                                ContainerPersonInfo(
                                    item = response.data
                                )
                            ),
                            works = listOf(ContainerWorks(list = response.data.works)),
                            characters = listOf(
                                ContainerCharacters(list = if (response.data.roles.isEmpty()) emptyList() else response.data.roles.first().characters)
                            )
                        )
                    )
                    _actionProgressBar.tryEmit(View.GONE)
                }
                is Results.Error -> {
                    _actionError.emit(response.exception.message.toString())
                }
            }
        }
    }

}