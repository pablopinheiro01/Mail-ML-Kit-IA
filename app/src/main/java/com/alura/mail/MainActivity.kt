package com.alura.mail

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.alura.mail.ui.navigation.HomeNavHost
import com.alura.mail.ui.theme.MAILTheme
import com.google.mlkit.nl.languageid.LanguageIdentification
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

                    val text = "Hello, mundo buenos dias !"

                    //identify probability about the language in text
                    val languageIdentifier = LanguageIdentification.getClient()
                    languageIdentifier.identifyPossibleLanguages(text)
                        .addOnSuccessListener { identifiedLanguages ->
                            for (identifiedLanguage in identifiedLanguages) {
                                val language = identifiedLanguage.languageTag
                                val confidence = identifiedLanguage.confidence
                                Log.i("language", "$language $confidence")
                            }
                        }
                        .addOnFailureListener {
                            // Model couldn’t be loaded or other internal error.
                            // ...
                            Log.i("error", "onCreate: $it")
                        }
                }
            }
        }
    }
}

