package com.alura.mail

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.alura.mail.mlkit.translatableLanguageModels
import com.alura.mail.ui.navigation.HomeNavHost
import com.alura.mail.ui.theme.MAILTheme
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.languageid.LanguageIdentifier
import com.google.mlkit.nl.languageid.LanguageIdentifier.UNDETERMINED_LANGUAGE_TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAILTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    HomeNavHost(navController = navController)

                    val text = "Hello world"

                    //identify probability about the language in text
                    languageIdentifier(
                        text,
                        onSuccess = {
                            Log.i("language", "$it")
                        },
                        onFailure = {
                            Log.i("language", "Language not identified")

                        }
                    )
                }
            }
        }
    }

    private fun languageIdentifier(
        text: String,
        onSuccess: (String) -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        val languageIdentifier = LanguageIdentification.getClient()
        languageIdentifier.identifyLanguage(text)
            .addOnSuccessListener { languageCode ->
                val languageName: String = translatableLanguageModels[languageCode]
                    ?: UNDETERMINED_LANGUAGE_TAG
                if (languageName != UNDETERMINED_LANGUAGE_TAG) {
                    onSuccess(languageName)
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

