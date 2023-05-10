package com.example.otaku.characters.ui

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otaku_data.utils.SharedPreferencesHelper
import com.example.otaku_domain.common.Results
import com.example.otaku_domain.usecases.anime.GetCharacterDetailsUseCase
import com.example.otaku.characters.adapters.info.ContainerCharacterInfo
import com.example.otaku.characters.ui.models.ActionCharacterData
import com.example.otaku.anime.details.info.adapters.franchises.ContainerFranchises
import com.example.otaku.anime.details.info.adapters.persons.ContainerPerson
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

    private val _actionInfo = MutableSharedFlow<ActionCharacterData>(replay = 1)
    val actionInfo: SharedFlow<ActionCharacterData> get() = _actionInfo

    private val _actionProgressBar = MutableSharedFlow<Int>(replay = 1)
    val actionProgressBar: SharedFlow<Int> get() = _actionProgressBar


    fun getCharacters(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = getCharacterDetailsUseCase.execute(id = id)) {
                is Results.Success -> {
                    _actionInfo.tryEmit(
                        ActionCharacterData(
                            character = listOf(
                                ContainerCharacterInfo(item = response.data)
                            ),
                            seyu = mutableListOf(
                                ContainerPerson(
                                    list = response.data.seyu,
                                    title = "Сейю"
                                )
                            ),
                            franchises = listOf(ContainerFranchises(list = response.data.animes))
                        )
                    )
                    _actionProgressBar.tryEmit(View.GONE)
                }
                is Results.Error -> {
                    _actionError.tryEmit(response.exception.message.toString())
                }
            }
        }
    }

}

