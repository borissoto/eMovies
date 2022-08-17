package com.boris.emovies.ui.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.boris.emovies.R
import com.boris.emovies.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${args.posterImageUrl}")
            .centerCrop().into(binding.backgroundImage)
        binding.movieTitle.text = args.title
        binding.description.text = args.overview
        binding.textLanguage.text = args.language
        binding.textYear.text = args.releaseDate.subSequence(0,4)
        binding.textRating.text = args.rating.toString()
    }
}