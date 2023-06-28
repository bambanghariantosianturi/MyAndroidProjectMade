package com.example.myandroidproject.core.data.source.remote.network

import com.example.myandroidproject.core.BuildConfig
import com.example.myandroidproject.core.data.source.remote.response.detailmovieresponse.DetailMovieResponse
import com.example.myandroidproject.core.data.source.remote.response.genremovieresponse.GenreMovieResponse
import com.example.myandroidproject.core.data.source.remote.response.listmovieresponse.ListMoviesResponse
import com.example.myandroidproject.core.data.source.remote.response.movietrailerresponse.ListMovieTrailerResponse
import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //https://api.themoviedb.org/3/discover/movie?api_key=be8b6c8aa9a5f4e240bb6093f9849051&page=1&with_genres=53
    @GET("discover/movie")
    fun getMovieList(
        @Query("api_key") movieApiKey: String? = BuildConfig.API_KEY,
        @Query("page") page: Int?,
        @Query("with_genres") genreId: Int?
    ): Flowable<ListMoviesResponse>

    //https://api.themoviedb.org/3/movie/497698?api_key=be8b6c8aa9a5f4e240bb6093f9849051
    @GET("movie/{movieId}")
    fun getDetailMovie(
        @Path("movieId") movieId: Int?,
        @Query("api_key") movieApiKey: String? = BuildConfig.API_KEY
    ): Flowable<DetailMovieResponse>

    //https://api.themoviedb.org/3/genre/movie/list?api_key=be8b6c8aa9a5f4e240bb6093f9849051
    @GET("genre/movie/list")
    fun getGenreMovies(
        @Query("api_key") movieApiKey: String? = BuildConfig.API_KEY
    ): Flowable<GenreMovieResponse>

    //https://api.themoviedb.org/3/movie/502356/videos?api_key=be8b6c8aa9a5f4e240bb6093f9849051
    @GET("movie/{movieId}/videos")
    fun getMovieTrailer(
        @Path("movieId") movieId: Int?,
        @Query("api_key") movieApiKey: String? = BuildConfig.API_KEY
    ): Flowable<ListMovieTrailerResponse>

    @GET("discover/movie")
    fun getListDoctor(
        @Query("api_key") movieApiKey: String? = BuildConfig.API_KEY
    ): Flowable<ListMoviesResponse>
}