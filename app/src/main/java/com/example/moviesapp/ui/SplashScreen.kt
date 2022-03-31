package com.example.moviesapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesapp.MovieActivity
import com.example.moviesapp.util.Constants.Companion.TOAST_TIME

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val intent = Intent(this, MovieActivity::class.java)
            startActivity(intent)
            finish()
        }, TOAST_TIME)
    }
}