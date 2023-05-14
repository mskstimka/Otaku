package com.example.otaku.persons.ui

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otaku.anime.details.info.adapters.characters.ContainerCharacters
import com.example.otaku.persons.adapters.info.ContainerPersonInfo
import com.example.otaku.persons.adapters.roles.ContainerWorks
import com.example.otaku.persons.ui.models.ActionPersonData
import com.example.otaku.utils.FavoriteAction
import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.user.Type
import com.example.otaku_domain.usecases.anime.GetPersonUseCase
import com.example.otaku_domain.usecases.favorites.CreateFavoriteUseCase
import com.example.otaku_domain.usecases.favorites.DeleteFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PersonViewModel @Inject constructor(
    private val getPersonUseCase: GetPersonUseCase,
    private val createFavoriteUseCase: CreateFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {

    private val _actionMessage = MutableSharedFlow<String>(replay = 1)
    val actionMessage: SharedFlow<String> get() = _actionMessage

    private val _actionInfo = MutableSharedFlow<ActionPersonData>(replay = 1)
    val actionInfo: SharedFlow<ActionPersonData> get() = _actionInfo

    private val _actionProgressBar = MutableSharedFlow<Int>(replay = 1)
    val actionProgressBar: SharedFlow<Int> get() = _actionProgressBar

    fun actionFavorites(favoriteAction: FavoriteAction) {
        viewModelScope.launch(Dispatchers.IO) {
            when (favoriteAction) {
                is FavoriteAction.CREATE_FAVORITE -> {
                    createFavorite(
                        linkedId = favoriteAction.linkedId,
                        linkedType = favoriteAction.linkedType
                    )
                }
                is FavoriteAction.DELETE_FAVORITE -> {
                    deleteFavorite(
                        linkedId = favoriteAction.linkedId,
                        linkedType = favoriteAction.linkedType
                    )
                }
            }
        }
    }

    private fun deleteFavorite(linkedId: Long, linkedType: Type) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = deleteFavoriteUseCase.execute(
                linkedId = linkedId,
                linkedType = linkedType.typeName
            )) {
                is Results.Success -> {
                    _actionMessage.tryEmit(response.data)
                }
                is Results.Error -> {
                    _actionMessage.tryEmit(response.exception.message.toString())
                }
            }
        }
    }

    private fun createFavorite(linkedId: Long, linkedType: Type) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = createFavoriteUseCase.execute(
                linkedId = linkedId,
                linkedType = linkedType.typeName
            )) {
                is Results.Success -> {
                    _actionMessage.tryEmit(response.data)
                }
                is Results.Error -> {
                    _actionMessage.tryEmit(response.exception.message.toString())
                }
            }
        }
    }

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
                    _actionMessage.emit(response.exception.message.toString())
                }
            }
        }
    }

}