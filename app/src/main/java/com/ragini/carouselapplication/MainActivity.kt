package com.ragini.carouselapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import androidx.recyclerview.widget.SnapHelper
import com.ragini.carouselapplication.databinding.ActivityMainBinding
import com.ragini.carouselapplication.model.Movie
import com.ragini.carouselapplication.network.MoviesRepository
import com.ragini.carouselapplication.view.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var popularMoviesPage: Int = 1
    private lateinit var binding: ActivityMainBinding
    private lateinit var popularMoviesAdapter: MovieAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager
    private lateinit var snapHelper: SnapHelper
    private lateinit var popularMovies: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        popularMovies = binding.recyclerView
        popularMoviesLayoutMgr = ProminentLayoutManager(this)
        popularMoviesAdapter = MovieAdapter(mutableListOf())
        snapHelper = PagerSnapHelper()

        with(binding.recyclerView) {
            setItemViewCacheSize(4)
            layoutManager = this@MainActivity.popularMoviesLayoutMgr
            adapter = this@MainActivity.popularMoviesAdapter
            animate()
        }
        snapHelper.attachToRecyclerView(binding.recyclerView)

        getPopularMovies()
    }

    private fun getPopularMovies() {
        MoviesRepository.getPopularMovies(
            popularMoviesPage,
            ::onPopularMoviesFetched,
            ::onError
        )
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.appendMovies(movies)
        val timer = Timer()
        timer.scheduleAtFixedRate(AutoScrollTask(), 1000, 5000)
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    // Sets Auto scroll with timer
    inner class AutoScrollTask : TimerTask() {
        private var position: Int = 0
        private var end: Boolean = false

        override fun run() {
            val currentPosition = popularMoviesLayoutMgr.findLastVisibleItemPosition()

            if (position == popularMoviesAdapter.itemCount - 1) {
                end = true
            } else if (position == 0) {
                end = false
            }
            if (!end && currentPosition == position) {
                position++
            } else if (!end && currentPosition != position) {
                position = currentPosition
            } else {
                getPopularMovies()
                end = false
            }
            popularMovies.smoothScrollToPosition(position)
        }
    }
}
