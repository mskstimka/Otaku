package com.example.otaku.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otaku_domain.usecases.translate.DownloadTranslateLanguagesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val downloadTranslateLanguagesUseCase: DownloadTranslateLanguagesUseCase
) : ViewModel() {

    fun downloadTranslateLanguages() {
        viewModelScope.launch(Dispatchers.IO) {
            downloadTranslateLanguagesUseCase.execute()
        }
    }
}