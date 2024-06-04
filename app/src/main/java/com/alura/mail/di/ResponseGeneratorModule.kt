package com.alura.mail.di

import com.alura.mail.mlkit.ResponseGenerator
import com.alura.mail.mlkit.TextTranslator
import com.alura.mail.util.FileUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ResponseGeneratorModule {
    @Provides
    fun provideResponseGenerator(): ResponseGenerator {
        return ResponseGenerator()
    }
}