package com.example.myandroidproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.example.myandroidproject.core.data.Resource
import com.example.myandroidproject.core.domain.model.genremoviemodel.GenreItemModel

interface IMainViewModel {

    fun getGenreMovie(): LiveData<Resource<List<GenreItemModel>>>
}