package com.anguy39.movieguess.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.anguy39.movieguess.R
import com.anguy39.movieguess.databinding.FragmentGameBinding
import com.anguy39.movieguess.databinding.FragmentResultsBinding

class ResultsFragment : Fragment() {
    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.playAgainButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_resultsFragment_to_welcomeFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val safeArgs = ResultsFragmentArgs.fromBundle(it)
            val numAnswers = safeArgs.numAnswers
            val correctAnswers = safeArgs.correctAnswers

            binding.correctAnswerTextView.text = correctAnswers.toString()
            binding.wrongAnswerTextView.text = (numAnswers - correctAnswers).toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}