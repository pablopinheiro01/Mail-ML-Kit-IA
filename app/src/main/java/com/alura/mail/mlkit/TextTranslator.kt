package com.alura.mail.mlkit

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.alura.mail.model.DownloadState
import com.alura.mail.model.Language
import com.alura.mail.model.LanguageModel
import com.alura.mail.util.FileUtil
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.languageid.LanguageIdentifier
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.TranslateRemoteModel
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.lang.Exception


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
            .addOnCompleteListener {
                languageIdentifier.close()
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
            .addOnCompleteListener {
                translator.close()
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
                if (models.any { it.language == modelCode }) {
                    onSuccess()
                } else {
                    onFailure()
                }
            }
            .addOnFailureListener {
                onFailure()
            }

    }

    fun downloadModel(
        modelName: String,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        val model = TranslateRemoteModel.Builder(modelName).build()
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        val modelManager = RemoteModelManager.getInstance()

        modelManager.download(model, conditions)
            .addOnSuccessListener {
                // Model downloaded.
                onSuccess()
            }
            .addOnFailureListener {
                // Error.
                onFailure()
            }
    }

    fun getAllModels(): List<LanguageModel> {
        return TranslateLanguage.getAllLanguages().map { model ->
            LanguageModel(
                id = model,
                name = translatableLanguageModels[model] ?: model,
                downloadState = DownloadState.NOT_DOWNLOADED,
                size = fileUtil.getSizeModel(model)
            )
        }
    }

    fun getDownloadedModels(
        onSuccess: (List<LanguageModel>) -> Unit = {},
        onFailure: () -> Unit = {}
    ) {

        val modelManager = RemoteModelManager.getInstance()

        modelManager.getDownloadedModels(TranslateRemoteModel::class.java)
            .addOnSuccessListener { models ->
                val languageModels = mutableListOf<LanguageModel>()
                models.forEach { model ->
                    try {
                        languageModels.add(
                            LanguageModel(
                                id = model.language,
                                name = translatableLanguageModels[model.language] ?: model.language,
                                downloadState = DownloadState.DOWNLOADED,
                                size = fileUtil.getSizeModel(model.modelNameForBackend)
                            )
                        )
                        Log.i(TAG, "getDownloadedModels: model: ${model.modelNameForBackend}")
                    } catch (e: Exception) {
                        Log.i(TAG, "getDownloadedModels: error no try catch $e ")
                    }
                }
                onSuccess(languageModels)
            }
            .addOnFailureListener {
                onFailure()
            }

    }

    fun removeModel(
        modelName: String,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        val model = TranslateRemoteModel.Builder(modelName).build()

        val modelManager = RemoteModelManager.getInstance()

        modelManager.deleteDownloadedModel(model)
            .addOnSuccessListener {
                // Model downloaded.
                onSuccess()
            }
            .addOnFailureListener {
                // Error.
                onFailure()
            }
    }


    companion object {
        const val TAG = "TextTranslator"
    }


}




