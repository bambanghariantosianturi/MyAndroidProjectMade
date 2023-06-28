package com.example.myandroidproject.favorite.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myandroidproject.core.commonconstant.CommonConstant
import com.example.myandroidproject.core.utils.ui.MovieAdapter
import com.example.myandroidproject.di.FavoriteModuleDependencies
import com.example.myandroidproject.favorite.component.DaggerFavoriteComponent
import com.example.myandroidproject.favorite.viewmodel.FavoriteViewModel
import com.example.myandroidproject.favorite.viewmodel.ViewModelFactory
import com.example.myandroidproject.favorite2.databinding.ActivityFavoriteBinding
import com.example.myandroidproject.kit.crossModuleNavigateTo
import com.example.myandroidproject.kit.getCrossModuleNavigator
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


class FavoriteActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels { factory }
    private val favoriteAdapter by lazy { MovieAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setUpView()
        observeData()
    }

    private fun observeData() {
        favoriteViewModel.getFavoriteMovie().observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.tvEmptyData.visibility = View.GONE
                binding.rvListFavorite.visibility = View.VISIBLE
                favoriteAdapter.setData(it)
            } else {
                binding.tvEmptyData.visibility = View.VISIBLE
                binding.rvListFavorite.visibility = View.GONE
            }
        })
    }

    private fun setUpView() {
        favoriteAdapter.onItemClick = { selectData ->
            crossModuleNavigateTo(
                getCrossModuleNavigator().classDetailActivity(),
                bundleOf().apply {
                    putParcelable(CommonConstant.MOVIE_ID, selectData)
                })
        }

        with(binding.rvListFavorite) {
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            this.layoutManager = staggeredGridLayoutManager
            this.setHasFixedSize(true)
            this.adapter = favoriteAdapter
        }
    }
}