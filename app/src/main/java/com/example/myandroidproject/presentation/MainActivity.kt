package com.example.myandroidproject.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.myandroidproject.core.data.Resource
import com.example.myandroidproject.core.domain.model.genremoviemodel.GenreItemModel
import com.example.myandroidproject.databinding.ActivityMainBinding
import com.example.myandroidproject.kit.gone
import com.example.myandroidproject.kit.visible
import com.example.myandroidproject.list.ui.ListMovieActivity
import com.example.myandroidproject.presentation.adapter.GenreAdapter
import com.example.myandroidproject.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IMainActivity {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val genreAdapter by lazy { GenreAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setUpView()
        observeData()
    }

    override fun setUpView() {
        with(binding.rvUser) {
            this.layoutManager = LinearLayoutManager(this@MainActivity, VERTICAL, false)
            this.setHasFixedSize(true)
            this.adapter = genreAdapter
        }

        genreAdapter.onItemClick = { selectData ->
            moveToListMovie(selectData)
        }
    }

    override fun observeData() {
        mainViewModel.getGenreMovie().observe(this, Observer {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        binding.apply {
                            pbGenreMovie.visible()
                            rvUser.gone()
                        }
                    }

                    is Resource.Success -> {
                        binding.apply {
                            pbGenreMovie.gone()
                            rvUser.visible()
                        }
                        genreAdapter.setDataGenre(it.data)
                        Toast.makeText(this, "Success get genre data", Toast.LENGTH_SHORT)
                            .show()
                    }

                    is Resource.Error -> {
                        Toast.makeText(this, "Error get genre data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun moveToListMovie(selectData: GenreItemModel) {
        val bundle = Bundle()
        bundle.putInt(ListMovieActivity.GENRE_MOVIE_ID, selectData.id)
        val intent = Intent(this@MainActivity, ListMovieActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}