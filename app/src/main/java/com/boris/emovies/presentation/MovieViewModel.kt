package com.boris.emovies.presentation

import android.util.Log
import androidx.lifecycle.*
import com.boris.emovies.core.Resource
import com.boris.emovies.data.model.Movie
import com.boris.emovies.data.model.MovieList
import com.boris.emovies.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.system.measureTimeMillis


class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private var operationStartTime = 0L


    private val _upcoming = MutableLiveData<Resource<List<Movie>>>()
    val upcoming: LiveData<Resource<List<Movie>>> get() = _upcoming

    private val _topRated = MutableLiveData<Resource<List<Movie>>>()
    val topRated: LiveData<Resource<List<Movie>>> get() = _topRated

    init {
        fetchMainViewMovies()
    }

    private fun fetchMainViewMovies() {
        _upcoming.postValue(Resource.Loading())
        _topRated.postValue(Resource.Loading())

        viewModelScope.launch(Dispatchers.IO) {

            val upcomingDeferred = async {
                repository.getPopularMovies()
            }
            val topRatedDeferred = async {
                repository.getTopRatedMovies()
            }

            try {
                val upcoming = upcomingDeferred.await()
                _upcoming.postValue(Resource.Success(upcoming.results))
            } catch (e: Exception) {
                _upcoming.postValue(Resource.Failure(e))
            }
            try {
                val topRated = topRatedDeferred.await()
                _topRated.postValue(Resource.Success(topRated.results))
            } catch (e: Exception) {
                _topRated.postValue(Resource.Failure(e))
            }


        }
    }
}

class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repository)
    }
}

