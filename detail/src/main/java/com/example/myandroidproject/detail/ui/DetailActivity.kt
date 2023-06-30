package com.example.myandroidproject.detail.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.example.myandroidproject.core.commonconstant.CommonConstant
import com.example.myandroidproject.core.domain.model.listmoviesmodel.MovieItemModel
import com.example.myandroidproject.detail.BuildConfig
import com.example.myandroidproject.detail.databinding.ActivityDetailBinding
import com.example.myandroidproject.detail.viewmodel.DetailViewModel
import com.example.myandroidproject.kit.crossModuleNavigateTo
import com.example.myandroidproject.kit.getCrossModuleNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), IDetailActivity {

    companion object {
        fun startActivity(activity: Activity, bundle: Bundle) {
            activity.startActivity(Intent(activity, DetailActivity::class.java).apply {
                putExtras(
                    bundle
                )
            })
        }
    }

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private var isFromListMovie: Boolean = false
    private var movieIdExtras: MovieItemModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val bundle = intent.extras
        if (bundle != null) {
            isFromListMovie = bundle.getBoolean(CommonConstant.IS_FROM_LIST_MOVIE)
            movieIdExtras = if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(CommonConstant.MOVIE_ID, MovieItemModel::class.java)
            } else {
                bundle.getParcelable(CommonConstant.MOVIE_ID)
            }

        }

        movieIdExtras?.let { setUpView(it) }
    }

    override fun setUpView(movieIdExtras: MovieItemModel) {
        Glide.with(binding.ivDetailMovie)
            .load(BuildConfig.IMG_URL + movieIdExtras.backdrop_path)
            .placeholder(com.example.myandroidproject.core.R.drawable.ic_movies_24)
            .into(binding.ivDetailMovie)

        binding.tvTitleMovie.text = movieIdExtras.title
        binding.tvDescMovie.text = movieIdExtras.overview

        var statusFavorite = movieIdExtras.isFavorite
        setStatusFavorite(statusFavorite)
        binding.ivFavorite.setOnClickListener {
            statusFavorite = !statusFavorite
            detailViewModel.setFavoriteMovie(movieIdExtras, statusFavorite)
            setStatusFavorite(statusFavorite)
        }
    }

    override fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    com.example.myandroidproject.core.R.drawable.ic_favorite_black_24
                )
            )
        } else {
            binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    com.example.myandroidproject.core.R.drawable.ic_favorite_border_24
                )
            )
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
        if (isFromListMovie) {
            crossModuleNavigateTo(
                getCrossModuleNavigator().classListMovieActivity(),
                bundleOf().apply {
                    putBoolean(CommonConstant.IS_FROM_DETAIL, true)
                })
        } else {
            isTaskRoot
        }
    }
}