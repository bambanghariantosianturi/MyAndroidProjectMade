package com.example.myandroidproject.di

import com.example.myandroidproject.core.domain.usecase.DataUseCase
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Alo-BambangHariantoSianturi on 28/06/23.
 */

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun dataUseCase(): DataUseCase
}