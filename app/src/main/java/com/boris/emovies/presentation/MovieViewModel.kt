package com.boris.emovies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.boris.emovies.core.Resource
import com.boris.emovies.repository.MovieRepository
import kotlinx.coroutines.Dispatchers


class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    fun fetchMainViewMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            emit(
                Resource.Success(
                    Pair(
                        repository.getUpcomingMovies(),
                        repository.getTopRatedMovies()
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repository)
    }
}

