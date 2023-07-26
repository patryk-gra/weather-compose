package com.andromasters.weather.di

import android.content.Context
import com.andromasters.weather.data.repository.LocalStorageRepositoryImpl
import com.andromasters.weather.domain.repository.LocalStorageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideStorageRepository(
        @ApplicationContext context: Context
    ): LocalStorageRepository =
        LocalStorageRepositoryImpl(context)
}