package com.example.myandroidproject.core.data.source.local.entity

import androidx.room.*

@Entity(tableName = "user", primaryKeys = ["id"])
data class DataEntity(
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo("adult")
    val adult: Boolean,

    @ColumnInfo("backdrop_path")
    val backdrop_path: String,

    @ColumnInfo("genre_ids")
    val genre_ids: List<Int>? = null,

    @ColumnInfo("original_language")
    val original_language: String,

    @ColumnInfo("original_title")
    val original_title: String,

    @ColumnInfo("overview")
    val overview: String,

    @ColumnInfo("popularity")
    val popularity: Double,

    @ColumnInfo("poster_path")
    val poster_path: String,

    @ColumnInfo("release_date")
    val release_date: String,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("video")
    val video: Boolean,

    @ColumnInfo("vote_average")
    val vote_average: Double,

    @ColumnInfo("vote_count")
    val vote_count: Int,

    @ColumnInfo("isFavorite")
    var isFavorite: Boolean = false
)