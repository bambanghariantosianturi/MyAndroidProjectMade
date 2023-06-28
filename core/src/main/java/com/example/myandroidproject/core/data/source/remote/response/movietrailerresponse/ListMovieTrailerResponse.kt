package com.example.myandroidproject.core.data.source.remote.response.movietrailerresponse

import com.google.gson.annotations.SerializedName

/**
 * Created by Alo-BambangHariantoSianturi on 12/06/23.
 */
data class ListMovieTrailerResponse(
    val id: Int?,
    @SerializedName("results")
    val movieTrailerItem: List<MovieTrailerItem>?,
) {

    data class MovieTrailerItem(
        val key: String?,
        val site: String?
    )
}