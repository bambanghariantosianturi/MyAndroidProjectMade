package com.example.myandroidproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.myandroidproject.core.data.Resource
import com.example.myandroidproject.core.domain.model.genremoviemodel.GenreItemModel
import com.example.myandroidproject.core.domain.usecase.DataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dataUseCase: DataUseCase) : ViewModel(),
    IMainViewModel {

    override fun getGenreMovie(): LiveData<Resource<List<GenreItemModel>>> {
        return LiveDataReactiveStreams.fromPublisher(dataUseCase.getGenreMovie())
    }
}