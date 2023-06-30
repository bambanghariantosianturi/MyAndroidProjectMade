package com.example.myandroidproject.core.domain.model.detailmoviemodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailMovie(
    val adult: Boolean,
    val backdrop_path: String,
    val budget: Int,
    val genres: List<Int>,
    val homepage: String,
    val id: Int?,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val isFavorite: Boolean
): Parcelable