package com.alura.mail.mlkit

import android.util.Log
import com.alura.mail.model.Language
import com.alura.mail.util.FileUtil
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.languageid.LanguageIdentifier
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.TranslateRemoteModel
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions


class TextTranslator(private val fileUtil: FileUtil) {

    fun languageIdentifier(
        text: String,
        onSuccess: (Language) -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        val languageIdentifier = LanguageIdentification.getClient()
        languageIdentifier.identifyLanguage(text)
            .addOnSuccessListener { languageCode ->
                val languageName: String = translatableLanguageModels[languageCode]
                    ?: LanguageIdentifier.UNDETERMINED_LANGUAGE_TAG
                if (languageName != LanguageIdentifier.UNDETERMINED_LANGUAGE_TAG) {
                    onSuccess(Language(name = languageName, code = languageCode))
                } else {
                    onFailure()
                }
            }
            .addOnFailureListener {
                // Model couldn’t be loaded or other internal error.
                // ...
                onFailure()
            }
    }

    fun textTranslate(
        text: String,
        sourceLanguage: String,
        targetLanguage: String,
        onSuccess: (String) -> Unit = {},
        onFailure: () -> Unit = {}
    ) {

        // Create an English-German translator:
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguage)
            .setTargetLanguage(targetLanguage)
            .build()

        val translator = Translation.getClient(options)

        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        translator.translate(text)
            .addOnSuccessListener { textTranslated ->
                onSuccess(textTranslated)
            }.addOnFailureListener {
                onFailure()
            }

    }

    fun verifyDownloadModule(
        modelCode: String,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
    ) {

        val modelManager = RemoteModelManager.getInstance()

// Get translation models stored on the device.
        modelManager.getDownloadedModels(TranslateRemoteModel::class.java)
            .addOnSuccessListener { models ->
                if(models.any{ it.language == modelCode }){
                    onSuccess()
                }else{
                    onFailure()
                }
            }
            .addOnFailureListener {
                onFailure()
            }

    }
}




