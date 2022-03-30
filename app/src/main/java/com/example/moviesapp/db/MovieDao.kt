package com.example.moviesapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviesapp.model.Result

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: Result): Long

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<Result>>

    @Delete
    suspend fun deleteMovieResult(movie: Result)
}