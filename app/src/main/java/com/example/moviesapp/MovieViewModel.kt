package com.example.moviesapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.model.MoviesResponse
import com.example.moviesapp.ui.MovieRepository
import com.example.moviesapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MovieViewModel(
    app: Application,
    private val movieRepository: MovieRepository
) : AndroidViewModel(app) {

    val popularMovies: MutableLiveData<Resource<MoviesResponse>> = MutableLiveData()
    var moviePage = 1
    var moviesResponse: MoviesResponse? = null

    init {
        getPopularMovies()
    }

    fun getPopularMovies() = viewModelScope.launch {
        safeMoviesCall()
    }

    private fun handleMoviesResponse(response: Response<MoviesResponse>): Resource<MoviesResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                moviePage++
                if (moviesResponse == null) {
                    moviesResponse = resultResponse
                } else {
                    val oldMovies = moviesResponse?.results
                    val newMovies = resultResponse.results
                    oldMovies?.addAll(newMovies)
                }
                return Resource.Success(moviesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeMoviesCall() {
        popularMovies.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = movieRepository.getPopularMovies(moviePage)
                popularMovies.postValue(handleMoviesResponse(response))
            } else {
                popularMovies.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> popularMovies.postValue(Resource.Error("Network Failure"))
                else -> popularMovies.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<MoviesApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}