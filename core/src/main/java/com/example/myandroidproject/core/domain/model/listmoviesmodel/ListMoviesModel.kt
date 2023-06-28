package com.example.myandroidproject.core.domain.model.listmoviesmodel

data class ListMoviesModel(
    val page: Int,
    val movieItemModels: List<MovieItemModel>,
    val total_pages: Int,
    val total_results: Int
)