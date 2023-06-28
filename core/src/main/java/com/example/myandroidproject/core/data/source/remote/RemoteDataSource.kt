package com.example.myandroidproject.core.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import com.example.myandroidproject.core.data.source.remote.network.ApiResponse
import com.example.myandroidproject.core.data.source.remote.network.ApiService
import com.example.myandroidproject.core.data.source.remote.response.detailmovieresponse.DetailMovieResponse
import com.example.myandroidproject.core.data.source.remote.response.genremovieresponse.GenreItemResponse
import com.example.myandroidproject.core.data.source.remote.response.listmovieresponse.MovieItemResponse
import com.example.myandroidproject.core.data.source.remote.response.movietrailerresponse.ListMovieTrailerResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    var tempPage: Int = 0
    var totalPage: Int = 0

    @SuppressLint("CheckResult")
    fun getListMovies(page: Int, genreId: Int): Flowable<ApiResponse<List<MovieItemResponse>>> {
        val client = apiService.getMovieList(page = page, genreId = genreId)
        val resultData = PublishSubject.create<ApiResponse<List<MovieItemResponse>>>()
        client.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
            .take(1).subscribe({ response ->
                val data = response.movieItemResponses
                resultData.onNext(if (data?.isNotEmpty() == true) ApiResponse.Success(data) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getDetailMovie(movieId: Int): Flowable<ApiResponse<DetailMovieResponse>> {
        val client = apiService.getDetailMovie(movieId = movieId)
        val resultData = PublishSubject.create<ApiResponse<DetailMovieResponse>>()
        client.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
            .take(1).subscribe({ response ->
                resultData.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getGenreMovie(): Flowable<ApiResponse<List<GenreItemResponse>>> {
        val client = apiService.getGenreMovies()
        val resultData = PublishSubject.create<ApiResponse<List<GenreItemResponse>>>()
        client.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
            .take(1).subscribe({ response ->
                val data = response.genreItemResponses
                resultData.onNext(if (data?.isNotEmpty() == true) ApiResponse.Success(data) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getMovieTrailer(movieId: Int): Flowable<ApiResponse<List<ListMovieTrailerResponse.MovieTrailerItem>>> {
        val client = apiService.getMovieTrailer(movieId = movieId)
        val resultData =
            PublishSubject.create<ApiResponse<List<ListMovieTrailerResponse.MovieTrailerItem>>>()
        client.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
            .take(1).subscribe({ response ->
                val data = response.movieTrailerItem
                resultData.onNext(if (data?.isNotEmpty() == true) ApiResponse.Success(data) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }
}



