package com.anguy39.movieguess.ui.main.game

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.anguy39.movieguess.R
import com.anguy39.movieguess.databinding.FragmentGameBinding
import com.anguy39.movieguess.model.Game
import java.util.*
import kotlin.concurrent.schedule

private const val TAG = "GameFragment"
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by navGraphViewModels(R.id.nav_graph)

    private var localAnswers = booleanArrayOf()
    private var selection = -1
    private var done = false

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
            done = true
            simulateGameOver()
        }


        answerAnimation(binding.answer1Button)
        answerAnimation(binding.answer2Button)
        answerAnimation(binding.answer3Button)

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
                evaluateAnimation()
//                Timer("SettingUp", false).schedule(WIN_ANIM_DURATION+100) {
//                    binding.nextQuestionButton.isEnabled = true
//                    binding.gameOverButton.isEnabled = true
//                }
//

                binding.nextQuestionButton.setOnClickListener {
                    viewModel.newQuestion()
                    it.findNavController().navigate(R.id.action_gameFragment_self2)
                }
                simulateGameOver()
            }
        }
    }

    fun simulateGameOver() {
        if (!done) done = (localAnswers.size > Game.NUM_QUESTIONS - 1)
        val correctAnswers = localAnswers.filter { it }.size

        if (done) {
            val actionResults = GameFragmentDirections.actionGameFragmentToResultsFragment()
            actionResults.correctAnswers = correctAnswers
            actionResults.numAnswers = localAnswers.size
            Timer("SettingUp", false).schedule(WIN_ANIM_DURATION+100) {
                Navigation.findNavController(binding.root).navigate(actionResults)
            }

        }

    }

    private fun answerAnimation (answerChoice: Button) {
        val animatorAnswer = ValueAnimator.ofFloat(600f, 0f)
        animatorAnswer.addUpdateListener {
            answerChoice.setTranslationY(it.animatedValue as Float)

        }
        animatorAnswer.interpolator = LinearInterpolator()
        animatorAnswer.duration = ANSWER_MOVE_DURATION
        animatorAnswer.start()
    }

    private fun evaluateAnimation() {
        val animatorSign = ValueAnimator.ofFloat(0f, 1f)
        animatorSign.addUpdateListener {
            val value = it.animatedValue as Float
            binding.correctSignImageView.alpha = value
        }

        animatorSign.interpolator = LinearInterpolator()
        animatorSign.duration = WIN_ANIM_DURATION
        animatorSign.start()
//        binding.nextQuestionButton.isEnabled = false
//        binding.gameOverButton.isEnabled = false
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

    companion object {
        const val ANSWER_MOVE_DURATION = 700L
        const val WIN_ANIM_DURATION = 800L
    }

}