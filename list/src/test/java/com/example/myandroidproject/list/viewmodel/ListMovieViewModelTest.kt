package com.example.myandroidproject.list.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.myandroidproject.core.data.DataRepository
import com.example.myandroidproject.core.data.source.remote.network.ApiService
import com.example.myandroidproject.core.domain.model.listmoviesmodel.MovieItemModel
import com.example.myandroidproject.core.domain.repository.IDataRepository
import com.example.myandroidproject.core.domain.usecase.DataUseCase
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Alo-BambangHariantoSianturi on 20/06/23.
 */

@RunWith(MockitoJUnitRunner::class)
class ListMovieViewModelTest {

    @Mock
    private lateinit var useCase: DataUseCase
    private lateinit var listMovieViewModel: ListMovieViewModel
    private val data = useCase.getMovieList(1, 1234)


    @Before
    fun setUp() {
        listMovieViewModel = ListMovieViewModel(useCase)
    }

    @Test
    fun `when Get List Should Not Null and Return Success`() {
        val expected = MutableLiveData<Result<List<MovieItemModel>>>() //Result diambil dari package data yang sudah disiapkan pada starter project
        expected.value = Result.success(useCase.getMovieList(1, 1234))
        `when`(useCase.getMovieList(11, 1234)).thenReturn(expected)
        val actualNews = listMovieViewModel.getMovieList(1, 1234)
        Assert.assertNotNull(actualNews)
    }
}