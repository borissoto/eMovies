package com.boris.emovies.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import com.boris.emovies.R
import com.boris.emovies.core.Resource
import com.boris.emovies.data.model.Movie
import com.boris.emovies.data.remote.MovieDataSource
import com.boris.emovies.databinding.FragmentMovieBinding
import com.boris.emovies.presentation.MovieViewModel
import com.boris.emovies.presentation.MovieViewModelFactory
import com.boris.emovies.repository.MovieRepositoryImpl
import com.boris.emovies.repository.RetrofitClient
import com.boris.emovies.ui.movie.adapters.MovieAdapter
import com.boris.emovies.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.boris.emovies.ui.movie.adapters.concat.UpcomingConcatAdapter

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {

    private lateinit var concatAdapter: ConcatAdapter
    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel> { MovieViewModelFactory(MovieRepositoryImpl(
        MovieDataSource(RetrofitClient.webService)
    )) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        viewModel.fetchMainViewMovies().observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressCircular.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(0, UpcomingConcatAdapter(MovieAdapter(it.data.first.results, this@MovieFragment)))
                        addAdapter(1, TopRatedConcatAdapter(MovieAdapter(it.data.second.results, this@MovieFragment)))
                    }

                    binding.rvMovies.adapter = concatAdapter

                }
                is Resource.Failure -> {
                    binding.progressCircular.visibility = View.GONE
                    Log.d("Livedata","${it.exception}")
                }
            }
        })
    }

    override fun onMovieClick(movie: Movie) {
        Log.d("MovieList","On Moi click ${movie}")
    }
}