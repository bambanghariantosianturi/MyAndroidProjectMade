package com.example.myandroidproject.core.di

import com.example.myandroidproject.core.data.DataRepository
import com.example.myandroidproject.core.domain.repository.IDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(dataRepository: DataRepository): IDataRepository
}

