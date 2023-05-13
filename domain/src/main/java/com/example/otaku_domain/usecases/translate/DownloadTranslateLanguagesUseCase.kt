package com.example.otaku_domain.usecases.translate

import com.example.otaku_domain.repository.TranslateRepository
import javax.inject.Inject

class DownloadTranslateLanguagesUseCase @Inject constructor(private val translateRepository: TranslateRepository) {
    suspend fun execute() = translateRepository.downloadTransalteLanguages()
}