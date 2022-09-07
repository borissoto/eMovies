package com.boris.emovies.domain

import com.boris.emovies.repository.MovieRepository

class RequestUpcomingMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke() = movieRepository.getUpcomingMovies()
}