package com.example.myandroidproject.list.ui

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
    }

    private lateinit var binding: ActivityListMovieBinding
    private val listViewModel: ListMovieViewModel by viewModels()
    private var genreMovieId: Int = 0
    private val movieAdapter by lazy { MovieAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setUpExtrasData()
        setUpView()
    }

    override fun onResume() {
        super.onResume()
        observeData(CommonConstant.PAGE_LIST_MOVIE, genreMovieId)
    }

    override fun observeData(page: Int, genreMovieId: Int) {
        listViewModel.getMovieList(page, genreMovieId).observe(this, Observer {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        binding.apply {
                            pbListMovie.visible()
                            rvListMovie.gone()
                        }
                    }

                    is Resource.Success -> {
                        binding.apply {
                            pbListMovie.gone()
                            rvListMovie.visible()
                            pbLoadMore.gone()
                        }
                        movieAdapter.setData(it.data)
                        Toast.makeText(
                            this,
                            "Success get list movie data",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }

                    is Resource.Error -> {
                        Toast.makeText(this, "Error get list movie data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    override fun setUpExtrasData() {
        val bundle = intent.extras
        if (bundle != null) {
            this.genreMovieId = bundle.getInt(GENRE_MOVIE_ID)
        }
    }

    override fun setUpView() {
        movieAdapter.onItemClick = { selectData ->
            crossModuleNavigateTo(
                getCrossModuleNavigator().classDetailActivity(),
                bundleOf().apply {
                    putParcelable(CommonConstant.MOVIE_ID, selectData)
                })
        }

        with(binding.rvListMovie) {
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            this.layoutManager = staggeredGridLayoutManager

            this.setHasFixedSize(true)
            this.adapter = movieAdapter

            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (listViewModel.getPage() < listViewModel.getTotalPage()) {
                            binding.pbLoadMore.visible()
                            observeData(listViewModel.getPage() + 1, genreMovieId)
                        }
                    }
                }
            })
        }

        binding.ivFavorite.setOnClickListener {
//            crossModuleNavigateTo(getCrossModuleNavigator().classFavoriteActivity())
//            startActivity(Intent(this, Class.forName("com.example.myandroidproject.favorite.ui.FavoriteActivity")))
            val uri = Uri.parse("myandroidproject://favorite")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }
}