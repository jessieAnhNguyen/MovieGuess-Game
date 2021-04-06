package com.anguy39.movieguess.ui.main

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
    private var character = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        binding.run {
            vm = sharedViewModel
            lifecycleOwner = this@ResultsFragment
        }
        character = sharedViewModel.getCharacter()

        characterRotateAnimation(displayCharacter(character))

        binding.playAgainButton.setOnClickListener {
            sharedViewModel.resetGame()
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
//            Log.d(TAG, "Result: character is " + character)
//            displayCharacter(character)
            binding.correctAnswerTextView.text = correctAnswers.toString()
            binding.wrongAnswerTextView.text = (numAnswers - correctAnswers).toString()
        }
    }

    fun displayCharacter(character: Int): ImageView {
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
        return binding.characterImageView
    }

    fun characterRotateAnimation(characterImage: ImageView) {
        val animator1 = ObjectAnimator.ofFloat(characterImage, "rotation", -ANGLE, ANGLE)
        val animator2 = ObjectAnimator.ofFloat(characterImage, "rotation", ANGLE, 0f)

//        val animator1 = ObjectAnimator.ofFloat(characterImage, "scaleX", -1f, 0f)
//        val animator2 = ObjectAnimator.ofFloat(characterImage, "scaleX", 0f, 1f)

        animator1.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {}
        })

        animator2.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                displayCharacter(character)
            }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {

            }
        })

        val set = AnimatorSet()
        set.play(animator1).before(animator2)
        set.duration = WelcomeFragment.MICKEY_ROTATE_DURATION
        set.start()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ANGLE = 60f
    }
}