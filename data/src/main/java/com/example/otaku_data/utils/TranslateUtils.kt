package com.example.otaku_data.utils

import androidx.core.text.parseAsHtml
import com.example.otaku_domain.models.PersonEntity
import com.example.otaku_domain.models.characters.CharacterDetailsEntity
import com.example.otaku_domain.models.details.AnimeDetailsEntity
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object TranslateUtils {

    suspend fun translatePersonToUkraine(
        item: PersonEntity,
        isDescriptionTranslate: Boolean,
        isNameTranslate: Boolean
    ): PersonEntity = withContext(Dispatchers.IO) {
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.RUSSIAN)
            .setTargetLanguage(TranslateLanguage.UKRAINIAN)
            .build()
        val translator = Translation.getClient(options)

        return@withContext try {
            translator.downloadModelIfNeeded(conditions).await()
            if (isDescriptionTranslate) {
                val description =
                    translator.translate(item.jobTitle.parseAsHtml().toString()).await()
                item.jobTitle = description
            }
            if (isNameTranslate) {
                val name = translator.translate(item.nameRu).await()
                item.nameRu = name
            }
            item
        } catch (e: Exception) {
            item
        }

    }

    suspend fun translateCharacterToUkraine(
        item: CharacterDetailsEntity,
        isDescriptionTranslate: Boolean,
        isNameTranslate: Boolean
    ): CharacterDetailsEntity = withContext(Dispatchers.IO) {
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.RUSSIAN)
            .setTargetLanguage(TranslateLanguage.UKRAINIAN)
            .build()
        val translator = Translation.getClient(options)

        return@withContext try {
            translator.downloadModelIfNeeded(conditions).await()
            if (isDescriptionTranslate) {
                val description =
                    translator.translate(item.description_html.parseAsHtml().toString()).await()
                item.description_html = description
            }
            if (isNameTranslate) {
                val name = translator.translate(item.nameRu ?: "").await()
                item.nameRu = name
            }
            item
        } catch (e: Exception) {
            item
        }
    }

    suspend fun translateAnimeDetailsToUkraine(
        item: AnimeDetailsEntity,
        isDescriptionTranslate: Boolean,
        isNameTranslate: Boolean
    ): AnimeDetailsEntity = withContext(Dispatchers.IO) {
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.RUSSIAN)
            .setTargetLanguage(TranslateLanguage.UKRAINIAN)
            .build()
        val translator = Translation.getClient(options)

        return@withContext try {
            translator.downloadModelIfNeeded(conditions).await()
            if (isDescriptionTranslate) {
                val description =
                    translator.translate(item.description_html.parseAsHtml().toString()).await()
                item.description_html = description
            }
            if (isNameTranslate) {
                val name = translator.translate(item.russian ?: "").await()
                item.russian = name
            }
            item
        } catch (e: Exception) {
            item
        }
    }

}