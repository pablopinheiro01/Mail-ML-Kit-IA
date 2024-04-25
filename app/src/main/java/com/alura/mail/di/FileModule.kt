package com.alura.mail.di

import android.content.Context
import com.alura.mail.util.FileUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FileModule {
    @Provides
    fun provideFileUtil(@ApplicationContext context: Context): FileUtil {
        return FileUtil(context)
    }
}