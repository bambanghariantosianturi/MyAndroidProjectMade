package com.example.myandroidproject.detail.ui

import com.example.myandroidproject.core.domain.model.listmoviesmodel.MovieItemModel
import com.example.myandroidproject.detail.databinding.ActivityDetailBinding

interface IDetailActivity {

    fun observeData(binding: ActivityDetailBinding?, movieId: MovieItemModel)
}