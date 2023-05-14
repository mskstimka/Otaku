package com.example.otaku.characters.ui

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otaku.anime.details.info.adapters.franchises.ContainerFranchises
import com.example.otaku.anime.details.info.adapters.persons.ContainerPerson
import com.example.otaku.characters.adapters.info.ContainerCharacterInfo
import com.example.otaku.characters.ui.models.ActionCharacterData
import com.example.otaku.utils.FavoriteAction
import com.example.otaku_data.utils.SharedPreferencesHelper
import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.user.Type
import com.example.otaku_domain.usecases.anime.GetCharacterDetailsUseCase
import com.example.otaku_domain.usecases.favorites.CreateFavoriteUseCase
import com.example.otaku_domain.usecases.favorites.DeleteFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    private val createFavoriteUseCase: CreateFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    private val _actionMessage = MutableSharedFlow<String>(replay = 1)
    val actionMessage: SharedFlow<String> get() = _actionMessage

    private val _actionInfo = MutableSharedFlow<ActionCharacterData>(replay = 1)
    val actionInfo: SharedFlow<ActionCharacterData> get() = _actionInfo

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
                    _actionMessage.tryEmit(response.exception.message.toString())
                }
            }
        }
    }

}

