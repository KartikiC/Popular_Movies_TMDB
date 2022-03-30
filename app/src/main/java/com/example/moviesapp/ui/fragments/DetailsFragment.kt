package com.example.moviesapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesapp.MovieActivity
import com.example.moviesapp.MovieViewModel
import com.example.moviesapp.R
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment(R.layout.fragment_details) {

    lateinit var viewModel: MovieViewModel
    val args: DetailsFragmentArgs by navArgs()
   // val sharedPrefFile = "comments_sharedpref"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MovieActivity).viewModel
        val result = args.result
        Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + result.posterPath)
            .into(ivMovieImage)
        tvTitle.text = result.originalTitle
        tvDescription.text = result.overview

       // val sharedPreferences: SharedPreferences? = context?.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)


      /* btnAddComment.setOnClickListener {
            val editor = sharedPreferences?.edit()
            editor?.apply {
                putString("comment",editTextComment.text.toString())
                apply()
                commit()
            }
        }*/

    }
}