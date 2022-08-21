package com.boris.emovies.repository

import com.boris.emovies.core.InternetCheck
import com.boris.emovies.data.local.LocalMovieDataSource
import com.boris.emovies.data.model.MovieList
import com.boris.emovies.data.model.toMovieEntity
import com.boris.emovies.data.remote.RemoteMovieDataSource

class MovieRepositoryImpl(
    private val dataSourceRemote: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource
) : MovieRepository {

    override suspend fun getUpcomingMovies(): MovieList {
        return if (InternetCheck.isNetworAvailable()) {
            dataSourceRemote.getUpcomingMovies().results.forEach {
                dataSourceLocal.saveMovie(it.toMovieEntity("upcoming"))
            }
            dataSourceLocal.getUpcomingMovies()
        } else {
            dataSourceLocal.getUpcomingMovies()
        }
    }

    override suspend fun getTopRatedMovies(): MovieList {
        return if (InternetCheck.isNetworAvailable()) {
            dataSourceRemote.getTopRatedMovies().results.forEach {
                dataSourceLocal.saveMovie(it.toMovieEntity("toprated"))
            }
            dataSourceLocal.getTopRatedMovies()
        } else {
            dataSourceLocal.getTopRatedMovies()
        }
    }
}