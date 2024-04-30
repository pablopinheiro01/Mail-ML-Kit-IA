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
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
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

                    // Create an English-German translator:
                    val options = TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.PORTUGUESE)
                        .build()
                    val translator = Translation.getClient(options)

                    val conditions = DownloadConditions.Builder()
                        .requireWifi()
                        .build()

                    translator.downloadModelIfNeeded(conditions)
                        .addOnSuccessListener {
                            Log.i("Main", "onCreate: modelo baixado")
                            translator.translate(text).addOnSuccessListener {
                                Log.i("Main", "text: $text, translated: $it ")
                            }.addOnFailureListener{
                                Log.e("Main", "onCreate: error $it", )
                            }
                        }
                        .addOnFailureListener{
                            Log.e("Main", "Modelo nao baixado $it", )
                        }




                }
            }
        }
    }

}

