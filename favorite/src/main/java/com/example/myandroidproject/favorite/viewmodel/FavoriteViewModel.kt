package com.example.myandroidproject.favorite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.myandroidproject.core.domain.model.listmoviesmodel.MovieItemModel
import com.example.myandroidproject.core.domain.usecase.DataUseCase
import javax.inject.Inject

/**
 * Created by Alo-BambangHariantoSianturi on 26/06/23.
 */

class FavoriteViewModel @Inject constructor(private val useCase: DataUseCase) : ViewModel() {

    fun getFavoriteMovie(): LiveData<List<MovieItemModel>> {
        return LiveDataReactiveStreams.fromPublisher(useCase.getFavoriteMovie())
    }
}