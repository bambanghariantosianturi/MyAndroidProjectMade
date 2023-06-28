package com.example.myandroidproject.core.data.source.remote.response.listmovieresponse

import com.google.gson.annotations.SerializedName

data class ListMoviesResponse(
    val page: Int?,
    @SerializedName("results")
    val movieItemResponses: List<MovieItemResponse>?,
    val total_pages: Int?,
    val total_results: Int?
)