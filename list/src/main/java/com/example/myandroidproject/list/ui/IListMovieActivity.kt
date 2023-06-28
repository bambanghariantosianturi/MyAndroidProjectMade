package com.example.myandroidproject.list.ui

interface IListMovieActivity {

    fun setUpExtrasData()

    fun setUpView()

    fun observeData(page: Int, genreMovieId: Int)
}