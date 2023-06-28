package com.example.myandroidproject.core.data.source.remote.response.genremovieresponse

import com.google.gson.annotations.SerializedName

data class GenreMovieResponse(
    @SerializedName("genres")
    val genreItemResponses: List<GenreItemResponse>?
)