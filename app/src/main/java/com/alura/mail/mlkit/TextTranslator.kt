package com.alura.mail.mlkit

import com.alura.mail.model.Language
import com.alura.mail.util.FileUtil
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.languageid.LanguageIdentifier


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

}


