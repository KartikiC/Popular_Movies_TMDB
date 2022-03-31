package com.example.moviesapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.db.MovieDatabase
import com.example.moviesapp.ui.MovieRepository
import com.example.moviesapp.ui.MovieViewModelProviderFactory
import com.example.moviesapp.util.Constants.Companion.SHARED_PREF_NAME
import com.example.moviesapp.util.Constants.Companion.SHARED_PRF_KEY

class MovieActivity : AppCompatActivity() {

    lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val movieRepository = MovieRepository(MovieDatabase(this))
        val viewModelProviderFactory = MovieViewModelProviderFactory(application, movieRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(MovieViewModel::class.java)

        val sharedPreferences = getSharedPreferences(
            SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
        var comment = sharedPreferences.getString(SHARED_PRF_KEY, "Not Found")
        Toast.makeText(this, "$comment", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }
}