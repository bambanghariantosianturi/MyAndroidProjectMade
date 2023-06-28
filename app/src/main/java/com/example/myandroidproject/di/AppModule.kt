package com.example.myandroidproject.di

import com.example.myandroidproject.core.domain.usecase.DataInteractor
import com.example.myandroidproject.core.domain.usecase.DataUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideDataUseCase(dataInteractor: DataInteractor): DataUseCase
}