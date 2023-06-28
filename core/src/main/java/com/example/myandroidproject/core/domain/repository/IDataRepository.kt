package com.example.myandroidproject.core.domain.repository

import androidx.lifecycle.LiveData
import com.example.myandroidproject.core.data.Resource
import com.example.myandroidproject.core.data.source.local.entity.DataEntity
import com.example.myandroidproject.core.domain.model.detailmoviemodel.DetailMovie
import com.example.myandroidproject.core.domain.model.genremoviemodel.GenreItemModel
import com.example.myandroidproject.core.domain.model.listmoviesmodel.MovieItemModel
import com.example.myandroidproject.core.domain.model.movietrailermodel.MovieTrailerItemModel
import io.reactivex.Flowable
import java.util.zip.DataFormatException

interface IDataRepository {

    fun getMovieListData(page: Int, genreId: Int): Flowable<Resource<List<MovieItemModel>>>

    fun getDetailMovieData(movieId: Int): Flowable<Resource<DetailMovie>>

    fun getGenreMovieData(): Flowable<Resource<List<GenreItemModel>>>
    
    fun getMovieTrailer(movieId: Int): Flowable<Resource<List<MovieTrailerItemModel>>>

    fun getPage(): Int

    fun getTotalPage(): Int

    fun getFavoriteMovie(): Flowable<List<MovieItemModel>>

    fun setFavoriteMovie(movieItemModel: MovieItemModel, state: Boolean)
}