package com.example.myandroidproject.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myandroidproject.core.domain.usecase.DataUseCase
import javax.inject.Inject

/**
 * Created by Alo-BambangHariantoSianturi on 28/06/23.
 */
class ViewModelFactory @Inject constructor(private val dataUseCase: DataUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(dataUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}