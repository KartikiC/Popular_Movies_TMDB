package com.example.moviesapp.model


import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val page: Int,
    val results: MutableList<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)