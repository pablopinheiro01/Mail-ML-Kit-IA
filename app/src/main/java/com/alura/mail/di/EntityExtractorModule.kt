package com.alura.mail.di

import com.alura.mail.mlkit.EntityExtractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class EntityExtractorModule {
    @Provides
    fun provideEntityExtractor(): EntityExtractor {
        return EntityExtractor()
    }
}