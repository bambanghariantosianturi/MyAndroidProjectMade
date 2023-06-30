package com.example.myandroidproject.list.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myandroidproject.core.commonconstant.CommonConstant
import com.example.myandroidproject.core.data.Resource
import com.example.myandroidproject.core.utils.ui.MovieAdapter
import com.example.myandroidproject.kit.crossModuleNavigateTo
import com.example.myandroidproject.kit.getCrossModuleNavigator
import com.example.myandroidproject.kit.gone
import com.example.myandroidproject.kit.visible
import com.example.myandroidproject.list.databinding.ActivityListMovieBinding
import com.example.myandroidproject.list.viewmodel.ListMovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListMovieActivity : AppCompatActivity(), IListMovieActivity {

    companion object {
        const val GENRE_MOVIE_ID = "GENRE_MOVIE_ID"

        fun startActivity(activity: Activity, bundle: Bundle) {
            activity.startActivity(Intent(activity, ListMovieActivity::class.java).apply {
                putExtras(
                    bundle
                )
            })
        }
    }

    private lateinit var binding: ActivityListMovieBinding
    private val listViewModel: ListMovieViewModel by viewModels()
    private var genreMovieId: Int = 0
    private var isFromDetail: Boolean = false
    private val movieAdapter by lazy { MovieAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val bundle = intent.extras
        if (bundle != null) {
            genreMovieId = bundle.getInt(GENRE_MOVIE_ID)
            isFromDetail = bundle.getBoolean(CommonConstant.IS_FROM_DETAIL)
        }
        setUpView()
    }

    override fun onResume() {
        super.onResume()
        observeData()
    }

    override fun observeData() {
        listViewModel.getMovieList(CommonConstant.PAGE_LIST_MOVIE, genreMovieId)
            .observe(this, Observer {
                if (it != null) {
                    when (it) {
                        is Resource.Loading -> {
                            with(binding) {
                                pbListMovie.visible()
                                pbLoadMore.gone()
                                rvListMovie.gone()

                            }
                        }

                        is Resource.Success -> {
                            with(binding) {
                                pbListMovie.gone()
                                rvListMovie.visible()
                                pbLoadMore.gone()
                            }
                            movieAdapter.clear()
                            movieAdapter.setData(it.data)
                            Toast.makeText(
                                this,
                                "Success get list movie data",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }

                        is Resource.Error -> {
                            Toast.makeText(this, "Error get list movie data", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            })
    }

    override fun setUpExtrasData(): Boolean {
        val bundle = intent.extras
        if (bundle != null) {
            this.genreMovieId = bundle.getInt(GENRE_MOVIE_ID)
        }

        return bundle != null
    }

    override fun setUpView() {
        movieAdapter.onItemClick = { selectData ->
            crossModuleNavigateTo(
                getCrossModuleNavigator().classDetailActivity(),
                bundleOf().apply {
                    putParcelable(CommonConstant.MOVIE_ID, selectData)
                    putBoolean(CommonConstant.IS_FROM_LIST_MOVIE, true)
                })
            finish()
        }

        with(binding.rvListMovie) {
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            this.layoutManager = staggeredGridLayoutManager

            this.setHasFixedSize(true)
            this.adapter = movieAdapter
        }

        binding.ivFavorite.setOnClickListener {
            val uri = Uri.parse("myandroidproject://favorite")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }
}