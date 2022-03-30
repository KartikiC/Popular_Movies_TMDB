package com.example.moviesapp.ui

import com.example.moviesapp.api.RetrofitInstance
import com.example.moviesapp.db.MovieDatabase

class MovieRepository(
    val db: MovieDatabase
) {
    suspend fun getPopularMovies(pageNumber: Int) =
        RetrofitInstance.api.getMovies(pageNumber)

}