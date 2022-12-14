package com.boris.emovies.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.boris.emovies.R
import com.boris.emovies.core.Resource
import com.boris.emovies.data.local.AppDatabase
import com.boris.emovies.data.local.LocalMovieDataSource
import com.boris.emovies.data.model.Movie
import com.boris.emovies.data.remote.RemoteMovieDataSource
import com.boris.emovies.databinding.FragmentMovieBinding
import com.boris.emovies.presentation.MovieViewModel
import com.boris.emovies.presentation.MovieViewModelFactory
import com.boris.emovies.repository.MovieRepositoryImpl
import com.boris.emovies.repository.RetrofitClient
import com.boris.emovies.ui.movie.adapters.MovieAdapter
import com.boris.emovies.ui.movie.adapters.concat.RecommendedConcatAdapter
import com.boris.emovies.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.boris.emovies.ui.movie.adapters.concat.UpcomingConcatAdapter

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {

    private lateinit var concatAdapter: ConcatAdapter
    private lateinit var binding: FragmentMovieBinding
//    var repository = MovieRepositoryImpl(RemoteMovieDataSource(RetrofitClient.webService), LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao()))
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.webService),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        viewModel.upcoming.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressUpcoming.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressUpcoming.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(
                            0,
                            UpcomingConcatAdapter(
                                MovieAdapter(
                                    it.data, this@MovieFragment
                                )
                            )
                        )
                    }
                    binding.rvMovies.adapter = concatAdapter
                }
                is Resource.Failure -> {
                    binding.progressUpcoming.visibility = View.GONE
                    Log.d("Livedata", "${it.exception}")
                }
            }
        })

        viewModel.topRated.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    binding.progressTopBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressTopBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(
                            TopRatedConcatAdapter(
                                MovieAdapter(
                                    it.data, this@MovieFragment
                                )
                            )
                        )
                    }
                    binding.rvMovies.adapter = concatAdapter
                }
                is Resource.Failure -> {
                    binding.progressTopBar.visibility = View.GONE
                    Log.d("Livedata", "${it.exception}")
                }
            }
        })
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToDetailFragment(
            movie.poster_path,
            movie.vote_average.toFloat(),
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date
        )
        findNavController().navigate(action)
    }
}