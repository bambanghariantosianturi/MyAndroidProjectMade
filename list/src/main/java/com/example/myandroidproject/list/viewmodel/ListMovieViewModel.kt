package com.example.myandroidproject.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.myandroidproject.core.data.Resource
import com.example.myandroidproject.core.domain.model.listmoviesmodel.MovieItemModel
import com.example.myandroidproject.core.domain.usecase.DataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListMovieViewModel @Inject constructor(private val useCase: DataUseCase): ViewModel(),
    IListMovieViewModel {

    override fun getMovieList(page: Int, genreId: Int): LiveData<Resource<List<MovieItemModel>>> {
        return LiveDataReactiveStreams.fromPublisher(useCase.getMovieList(page, genreId))
    }

    override fun getPage(): Int = useCase.getPage()

    override fun getTotalPage(): Int = useCase.getTotalPage()
}