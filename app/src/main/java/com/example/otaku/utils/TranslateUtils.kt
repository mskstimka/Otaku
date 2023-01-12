package com.example.otaku.utils

import androidx.core.text.parseAsHtml
import androidx.lifecycle.viewModelScope
import com.example.animator_domain.models.PersonEntity
import com.example.animator_domain.models.characters.CharacterDetailsEntity
import com.example.animator_domain.models.details.AnimeDetailsEntity
import com.example.animator_domain.models.details.roles.Character
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object TranslateUtils {


    fun translatePersonToUkraine(
        item: PersonEntity,
        isDescriptionTranslate: Boolean,
        isNameTranslate: Boolean,
        actionName: (translatedText: String) -> Unit,
        actionDescription: (translatedText: String) -> Unit
    ) {
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.RUSSIAN)
            .setTargetLanguage(TranslateLanguage.UKRAINIAN)
            .build()
        val translator = Translation.getClient(options)


        translator.downloadModelIfNeeded(conditions).addOnSuccessListener {
            if (isDescriptionTranslate) {
                translator.translate(item.jobTitle)
                    .addOnSuccessListener {
                        actionDescription(it)
                    }
            }
            if (isNameTranslate) {
                translator.translate(item.nameRu)
                    .addOnSuccessListener {
                        actionName(it)
                    }
            }
        }

    }

    fun translateCharacterToUkraine(
        item: CharacterDetailsEntity,
        isDescriptionTranslate: Boolean,
        isNameTranslate: Boolean,
        actionName: (translatedText: String) -> Unit,
        actionDescription: (translatedText: String) -> Unit
    ) {

        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.RUSSIAN)
            .setTargetLanguage(TranslateLanguage.UKRAINIAN)
            .build()
        val translator = Translation.getClient(options)


        translator.downloadModelIfNeeded(conditions).addOnSuccessListener {
            if (isDescriptionTranslate) {
                translator.translate(item.description_html.parseAsHtml().toString())
                    .addOnSuccessListener {
                        actionDescription(it)
                    }
            }
            if (isNameTranslate) {
                translator.translate(item.nameRu!!)
                    .addOnSuccessListener {
                        actionName(it)
                    }
            }
        }


    }

    suspend fun translateToUkraine(
        item: AnimeDetailsEntity,
        isDescriptionTranslate: Boolean,
        isNameTranslate: Boolean,
        actionName: (translatedText: String) -> Unit,
        actionDescription: (translatedText: String) -> Unit
    ) {

        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.RUSSIAN)
            .setTargetLanguage(TranslateLanguage.UKRAINIAN)
            .build()
        val translator = Translation.getClient(options)


        translator.downloadModelIfNeeded(conditions).addOnSuccessListener {
            if (isDescriptionTranslate) {
                translator.translate(item.description_html.parseAsHtml().toString())
                    .addOnSuccessListener {
                        actionDescription(it)
                    }
            }
            if (isNameTranslate) {
                translator.translate(item.russian!!)
                    .addOnSuccessListener {
                        actionName(it)
                    }
            }
        }
    }


    fun downloadLanguage() {
        var conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.RUSSIAN)
            .setTargetLanguage(TranslateLanguage.UKRAINIAN)
            .build()
        val translator = Translation.getClient(options)

        translator.downloadModelIfNeeded(conditions)
    }
}