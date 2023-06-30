package com.example.myandroidproject.core.data

import com.example.myandroidproject.core.data.source.local.LocalDataSource
import com.example.myandroidproject.core.data.source.remote.RemoteDataSource
import com.example.myandroidproject.core.data.source.remote.network.ApiResponse
import com.example.myandroidproject.core.data.source.remote.response.listmovieresponse.MovieItemResponse
import com.example.myandroidproject.core.data.source.remote.response.movietrailerresponse.ListMovieTrailerResponse
import com.example.myandroidproject.core.domain.model.detailmoviemodel.DetailMovie
import com.example.myandroidproject.core.domain.model.genremoviemodel.GenreItemModel
import com.example.myandroidproject.core.domain.model.listmoviesmodel.MovieItemModel
import com.example.myandroidproject.core.domain.model.movietrailermodel.MovieTrailerItemModel
import com.example.myandroidproject.core.domain.repository.IDataRepository
import com.example.myandroidproject.core.utils.AppExecutors
import com.example.myandroidproject.core.utils.DataMapper
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IDataRepository {

    override fun getMovieListData(
        page: Int,
        genreId: Int
    ): Flowable<Resource<List<MovieItemModel>>> {
        return object : NetworkBoundResource<List<MovieItemModel>, List<MovieItemResponse>>() {

            override fun loadFromDB(): Flowable<List<MovieItemModel>> {
                return localDataSource.getAllData().map {
                    DataMapper.MovieListMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MovieItemModel>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun createCall(): Flowable<ApiResponse<List<MovieItemResponse>>> {
                return remoteDataSource.getListMovies(page, genreId)
            }

            override fun saveCallResult(data: List<MovieItemResponse>) {
                val dataList = DataMapper.MovieListMapper.mapResponseToEntities(data)
                localDataSource.insertDataMovie(dataList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }.asFlowable()
    }

    override fun getDetailMovieData(movieId: Int): Flowable<Resource<DetailMovie>> {
        val result = PublishSubject.create<Resource<DetailMovie>>()
        val mCompositeDisposable = CompositeDisposable()
        result.onNext(Resource.Loading(null))
        val response = remoteDataSource.getDetailMovie(movieId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).take(1).doOnComplete {
                mCompositeDisposable.dispose()
            }.subscribe { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        result.onNext(
                            Resource.Success(
                                DataMapper.DetailMovieMapper.mapResponseToDomain(
                                    response.data
                                )
                            )
                        )
                    }

                    is ApiResponse.Empty -> {
                        result.onNext(
                            Resource.Success(
                                DataMapper.DetailMovieMapper.mapResponseToDomain(
                                    input = null
                                )
                            )
                        )
                    }

                    is ApiResponse.Error -> {
                        result.onNext(Resource.Error(response.errorMessage, null))
                    }
                }
            }
        mCompositeDisposable.add(response)

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun getGenreMovieData(): Flowable<Resource<List<GenreItemModel>>> {
        val result = PublishSubject.create<Resource<List<GenreItemModel>>>()
        val mCompositeDisposable = CompositeDisposable()
        result.onNext(Resource.Loading(null))
        val response = remoteDataSource.getGenreMovie()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .doOnComplete {
                mCompositeDisposable.dispose()
            }
            .subscribe { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        result.onNext(
                            Resource.Success(
                                DataMapper.GenreMovie.mapResponsesToEntities(
                                    response.data
                                )
                            )
                        )
                    }

                    is ApiResponse.Empty -> {
                        result.onNext(
                            Resource.Success(
                                DataMapper.GenreMovie.mapResponsesToEntities(
                                    listOf()
                                )
                            )
                        )
                    }

                    is ApiResponse.Error -> {
                        result.onNext(Resource.Error(response.errorMessage, null))
                    }
                }
            }
        mCompositeDisposable.add(response)
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun getMovieTrailer(movieId: Int): Flowable<Resource<List<MovieTrailerItemModel>>> {

        return object :
            NetworkBoundResource<List<MovieTrailerItemModel>, List<ListMovieTrailerResponse.MovieTrailerItem>>() {
            override fun loadFromDB(): Flowable<List<MovieTrailerItemModel>> {
                return Flowable.empty()
            }

            override fun createCall(): Flowable<ApiResponse<List<ListMovieTrailerResponse.MovieTrailerItem>>> {
                return remoteDataSource.getMovieTrailer(movieId)
            }

            override fun saveCallResult(data: List<ListMovieTrailerResponse.MovieTrailerItem>) {
                DataMapper.MovieTrailer.mapResponsesToDomain(data)
            }

            override fun shouldFetch(data: List<MovieTrailerItemModel>?): Boolean {
                return true
            }

        }.asFlowable()
    }

    override fun getPage(): Int = remoteDataSource.tempPage

    override fun getTotalPage(): Int = remoteDataSource.totalPage

    override fun getFavoriteMovie(): Flowable<List<MovieItemModel>> {
        return localDataSource.getFavoriteMovie()
            .map { DataMapper.MovieListMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteMovie(movieItemModel: MovieItemModel, state: Boolean) {
        val data = DataMapper.MovieListMapper.mapDomainToEntities(movieItemModel)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(data, state) }
    }
}