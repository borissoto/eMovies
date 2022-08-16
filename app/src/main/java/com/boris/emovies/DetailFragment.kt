package com.boris.emovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boris.emovies.databinding.FragmentDetailBinding
import com.boris.emovies.databinding.FragmentMovieBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
    }
}