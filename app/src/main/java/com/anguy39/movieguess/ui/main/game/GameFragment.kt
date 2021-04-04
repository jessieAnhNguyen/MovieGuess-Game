package com.anguy39.movieguess.ui.main.game

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.anguy39.movieguess.R
import com.anguy39.movieguess.databinding.FragmentGameBinding
import com.anguy39.movieguess.model.Game

private const val TAG = "GameFragment"
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by navGraphViewModels(R.id.nav_graph)

    private var localAnswers = booleanArrayOf()
    private var selection = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        binding.run {
            vm = viewModel
            lifecycleOwner = this@GameFragment
        }

        binding.correctSignImageView.visibility = View.INVISIBLE
        binding.correctTextView.visibility = View.INVISIBLE

        binding.gameOverButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_gameFragment_to_resultsFragment)
        }

        binding.answer1Button.setOnClickListener {
            selection = 0
            evaluateAns(0)
            binding.answer1Button.isEnabled = false
            binding.answer2Button.isEnabled = false
            binding.answer3Button.isEnabled = false
        }

        binding.answer2Button.setOnClickListener {
            selection = 1
            evaluateAns(1)
            binding.answer1Button.isEnabled = false
            binding.answer2Button.isEnabled = false
            binding.answer3Button.isEnabled = false
        }

        binding.answer3Button.setOnClickListener {
            selection = 2
            evaluateAns(2)
            binding.answer1Button.isEnabled = false
            binding.answer2Button.isEnabled = false
            binding.answer3Button.isEnabled = false
        }
        return binding.root
    }

    private fun evaluateAns(selection: Int) {
        if (selection >= 0) {
            viewModel.answerQuestion(selection)
            if (localAnswers.isNotEmpty()) {
                if (localAnswers.last()) {
                    binding.correctSignImageView.setImageResource(R.drawable.correct_sign)
                    binding.correctTextView.text = getText(R.string.correct_answer)

                }
                else {
                    binding.correctSignImageView.setImageResource(R.drawable.incorrect_sign_new)
                    binding.correctTextView.text = getText(R.string.incorrect_answer)
                }
                binding.correctSignImageView.visibility = View.VISIBLE
                binding.correctTextView.visibility = View.VISIBLE

//                val correctAnswers = localAnswers.filter { it }.size
//                binding.showResultsButton.visibility = if (done) View.VISIBLE else View.INVISIBLE
//                binding.showResultsButton.setOnClickListener {
//                    val actionResults = EvalFragmentDirections.actionEvalFragmentToResultsFragment(correctAnswers, answers.size)
//                    Navigation.findNavController(view).navigate(actionResults)
//                }
            }
            val done = (localAnswers.size > Game.NUM_QUESTIONS - 1)
            binding.nextQuestionButton.visibility = if (done) View.INVISIBLE else View.VISIBLE


            binding.nextQuestionButton.setOnClickListener {
                viewModel.newQuestion()
                it.findNavController().navigate(R.id.action_gameFragment_self)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.answers.observe(viewLifecycleOwner, {
            localAnswers = it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}