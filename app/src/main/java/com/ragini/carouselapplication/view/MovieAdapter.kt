package com.ragini.carouselapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ragini.carouselapplication.R
import com.ragini.carouselapplication.model.Movie

class MovieAdapter(
    private var movies: MutableList<Movie>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.itemView.layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.MATCH_PARENT
        )
        holder.bind(movie)
    }

    fun appendMovies(movies: List<Movie>) {
        if (movies.size > itemCount)
            removeMovies()

        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            itemCount
        )
    }

    private fun removeMovies() {
        for (i in 0 until itemCount)
            notifyItemRangeRemoved(i, itemCount)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)
        private var title: TextView = itemView.findViewById(R.id.movie_name)

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .fitCenter()
                .centerCrop()
                .into(poster)

            title.text = movie.title
        }
    }
}