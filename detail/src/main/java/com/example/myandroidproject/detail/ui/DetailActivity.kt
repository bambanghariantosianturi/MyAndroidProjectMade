package com.example.myandroidproject.detail.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.myandroidproject.core.commonconstant.CommonConstant
import com.example.myandroidproject.core.data.Resource
import com.example.myandroidproject.core.domain.model.listmoviesmodel.MovieItemModel
import com.example.myandroidproject.detail.BuildConfig
import com.example.myandroidproject.detail.R
import com.example.myandroidproject.detail.databinding.ActivityDetailBinding
import com.example.myandroidproject.detail.viewmodel.DetailViewModel
import com.example.myandroidproject.kit.gone
import com.example.myandroidproject.kit.visible
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val movieIdExtras = if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(CommonConstant.MOVIE_ID, MovieItemModel::class.java)
        } else {
            intent.getParcelableExtra(CommonConstant.MOVIE_ID)
        }

        if (movieIdExtras != null) {
            observeData(binding, movieIdExtras)
            setUpView(movieIdExtras)
        }
    }

    private fun setUpView(movieIdExtras: MovieItemModel) {
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

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.ivFavorite.setImageDrawable(ContextCompat.getDrawable(this, com.example.myandroidproject.core.R.drawable.ic_favorite_black_24))
        } else {
            binding.ivFavorite.setImageDrawable(ContextCompat.getDrawable(this, com.example.myandroidproject.core.R.drawable.ic_favorite_border_24))
        }
    }

    override fun observeData(binding: ActivityDetailBinding?, movieId: MovieItemModel) {
        detailViewModel.getMovieTrailer(movieId = movieId.id ?: 0).observe(this, Observer {
            if (it.data != null) {
                when (it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        Toast.makeText(this, "Success get movie trailer", Toast.LENGTH_SHORT)
                            .show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(this, "Error get detail movie data", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }
}