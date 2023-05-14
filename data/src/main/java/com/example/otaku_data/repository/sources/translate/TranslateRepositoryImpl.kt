package com.example.otaku_data.repository.sources.translate

import com.example.otaku_domain.repository.TranslateRepository
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TranslateRepositoryImpl @Inject constructor() : TranslateRepository {

    override suspend fun downloadTransalteLanguages() {

        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.RUSSIAN)
            .setTargetLanguage(TranslateLanguage.UKRAINIAN)
            .build()
        val translator = Translation.getClient(options)

        translator.downloadModelIfNeeded(conditions).await()
    }

}