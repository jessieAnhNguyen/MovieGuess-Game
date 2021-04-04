package com.anguy39.movieguess.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.anguy39.movieguess.R
import com.anguy39.movieguess.databinding.FragmentGameBinding
import com.anguy39.movieguess.databinding.FragmentResultsBinding
import com.anguy39.movieguess.ui.main.game.GameViewModel

private const val TAG = "ResultsFragment"
class ResultsFragment : Fragment() {
    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        binding.run {
            vm = sharedViewModel
            lifecycleOwner = this@ResultsFragment
        }

        binding.playAgainButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_resultsFragment_to_welcomeFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val safeArgs = ResultsFragmentArgs.fromBundle(it)
            val numAnswers = safeArgs.numAnswers
            val correctAnswers = safeArgs.correctAnswers ?: 0
            val character = sharedViewModel.getCharacter()
//            Log.d(TAG, "Result: character is " + character)

            when (character) {
                0 -> {
                    binding.characterImageView.setImageResource(R.drawable.olaf)
                    binding.characterResultTextView.text = getText(R.string.olaf_result)
                }
                1 -> {
                    binding.characterImageView.setImageResource(R.drawable.maleficent)
                    binding.characterResultTextView.text = getText(R.string.maleficent_result)
                }
                2 -> {
                    binding.characterImageView.setImageResource(R.drawable.cinderella)
                    binding.characterResultTextView.text = getText(R.string.cinderella_result)
                }
            }

            binding.correctAnswerTextView.text = correctAnswers.toString()
            binding.wrongAnswerTextView.text = (numAnswers - correctAnswers).toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}