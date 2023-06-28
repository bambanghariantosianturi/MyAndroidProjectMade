package com.example.myandroidproject.core.utils

import com.example.myandroidproject.core.data.source.local.entity.DataEntity
import com.example.myandroidproject.core.data.source.local.entity.GenreEntity
import com.example.myandroidproject.core.data.source.remote.response.detailmovieresponse.DetailMovieResponse
import com.example.myandroidproject.core.data.source.remote.response.genremovieresponse.GenreItemResponse
import com.example.myandroidproject.core.data.source.remote.response.listmovieresponse.MovieItemResponse
import com.example.myandroidproject.core.data.source.remote.response.movietrailerresponse.ListMovieTrailerResponse
import com.example.myandroidproject.core.domain.model.detailmoviemodel.DetailMovie
import com.example.myandroidproject.core.domain.model.genremoviemodel.GenreItemModel
import com.example.myandroidproject.core.domain.model.listmoviesmodel.MovieItemModel
import com.example.myandroidproject.core.domain.model.movietrailermodel.MovieTrailerItemModel

object DataMapper {

    object MovieListMapper {
        fun mapResponseToEntities(input: List<MovieItemResponse>): List<DataEntity> {
            val dataList = ArrayList<DataEntity>()
            input.map {
                val data = DataEntity(
                    id = it.id ?: 0,
                    adult = it.adult ?: false,
                    backdrop_path = it.backdrop_path.orEmpty(),
                    genre_ids = it.genre_ids ?: listOf(),
                    original_language = it.original_language.orEmpty(),
                    original_title = it.original_title.orEmpty(),
                    overview = it.overview.orEmpty(),
                    popularity = it.popularity ?: 0.0,
                    poster_path = it.poster_path.orEmpty(),
                    release_date = it.release_date.orEmpty(),
                    title = it.title.orEmpty(),
                    video = it.video ?: false,
                    vote_average = it.vote_average ?: 0.0,
                    vote_count = it.vote_count ?: 0,
                    isFavorite = false
                )
                dataList.add(data)
            }
            return dataList
        }


        fun mapEntitiesToDomain(input: List<DataEntity>): List<MovieItemModel> {
            val dataList = ArrayList<MovieItemModel>()
            input.map {
                val data = MovieItemModel(
                    id = it.id ?: 0,
                    adult = it.adult ?: false,
                    backdrop_path = it.backdrop_path.orEmpty(),
                    genre_ids = it.genre_ids ?: listOf(),
                    original_language = it.original_language.orEmpty(),
                    original_title = it.original_title.orEmpty(),
                    overview = it.overview.orEmpty(),
                    popularity = it.popularity ?: 0.0,
                    poster_path = it.poster_path.orEmpty(),
                    release_date = it.release_date.orEmpty(),
                    title = it.title.orEmpty(),
                    video = it.video ?: false,
                    vote_average = it.vote_average ?: 0.0,
                    vote_count = it.vote_count ?: 0,
                    isFavorite = it.isFavorite
                )
                dataList.add(data)
            }
            return dataList
        }

        fun mapDomainToEntities(input: MovieItemModel): DataEntity {
            return DataEntity(
                id = input.id ?: 0,
                adult = input.adult ?: false,
                backdrop_path = input.backdrop_path.orEmpty(),
                genre_ids = input.genre_ids ?: listOf(),
                original_language = input.original_language.orEmpty(),
                original_title = input.original_title.orEmpty(),
                overview = input.overview.orEmpty(),
                popularity = input.popularity ?: 0.0,
                poster_path = input.poster_path.orEmpty(),
                release_date = input.release_date.orEmpty(),
                title = input.title.orEmpty(),
                video = input.video ?: false,
                vote_average = input.vote_average ?: 0.0,
                vote_count = input.vote_count ?: 0,
                isFavorite = input.isFavorite
            )
        }
    }

    object DetailMovieMapper {
        fun mapResponseToDomain(input: DetailMovieResponse?): DetailMovie {
            return DetailMovie(
                adult = input?.adult,
                backdrop_path = input?.backdrop_path,
                budget = input?.budget,
                genres = listOf(),
                homepage = input?.homepage,
                id = input?.id,
                imdb_id = input?.imdb_id,
                original_language = input?.original_language,
                original_title = input?.original_title,
                overview = input?.overview,
                popularity = input?.popularity,
                poster_path = input?.poster_path,
                release_date = input?.release_date,
                title = input?.original_title,
                video = input?.video,
                vote_average = input?.vote_average,
                vote_count = input?.vote_count
            )
        }
    }

    object GenreMovie {
        fun mapResponsesToEntities(input: List<GenreItemResponse>): List<GenreItemModel> {
            val dataList = ArrayList<GenreItemModel>()
            input.map {
                val data = GenreItemModel(
                    id = it.id ?: 0,
                    name = it.name.orEmpty()
                )
                dataList.add(data)
            }
            return dataList
        }

        fun mapEntitiesToDomain(input: List<GenreEntity>): List<GenreItemModel> {
            return input.map {
                GenreItemModel(
                    id = it.id,
                    name = it.name
                )
            }
        }
    }

    object MovieTrailer {
        fun mapResponsesToDomain(input: List<ListMovieTrailerResponse.MovieTrailerItem>): List<MovieTrailerItemModel> {
            val dataList = ArrayList<MovieTrailerItemModel>()
            input.map {
                val data = MovieTrailerItemModel(
                    key = it.key.orEmpty(),
                    site = it.site.orEmpty()
                )
                dataList.add(data)
            }
            return dataList
        }
    }
}