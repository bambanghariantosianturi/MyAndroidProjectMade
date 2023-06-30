package com.example.myandroidproject.core.domain.usecase

import com.example.myandroidproject.core.data.Resource
import com.example.myandroidproject.core.domain.model.detailmoviemodel.DetailMovie
import com.example.myandroidproject.core.domain.model.genremoviemodel.GenreItemModel
import com.example.myandroidproject.core.domain.model.listmoviesmodel.MovieItemModel
import com.example.myandroidproject.core.domain.model.movietrailermodel.MovieTrailerItemModel
import io.reactivex.Flowable

interface DataUseCase {

    fun getMovieList(page: Int, genreId: Int): Flowable<Resource<List<MovieItemModel>>>

    fun getDetailMovieData(movieId: Int): Flowable<Resource<DetailMovie>>

    fun getGenreMovie(): Flowable<Resource<List<GenreItemModel>>>

    fun getMovieTrailer(movieId: Int): Flowable<Resource<List<MovieTrailerItemModel>>>

    fun getPage(): Int

    fun getTotalPage(): Int

    fun getFavoriteMovie(): Flowable<List<MovieItemModel>>

    fun setFavoriteMovie(movieItemModel: MovieItemModel, state: Boolean)
}