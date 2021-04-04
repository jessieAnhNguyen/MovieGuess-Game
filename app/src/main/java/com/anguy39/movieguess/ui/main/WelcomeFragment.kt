package com.anguy39.movieguess.ui.main

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import com.anguy39.movieguess.R
import com.anguy39.movieguess.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        val view = binding.root


        mickeyRotateAnimation(binding.mickeyWelcomeImageView)
        binding.beginGameButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_welcomeFragment_to_configFragment)
        }

        binding.settingsWelcomeButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_welcomeFragment_to_settingsFragment)
        }
        return view
    }

    fun mickeyRotateAnimation(welcomeImage: ImageView) {
        val animator1 = ObjectAnimator.ofFloat(welcomeImage, "scaleX", -1f, 0f)
        val animator2 = ObjectAnimator.ofFloat(welcomeImage, "scaleX", 0f, 1f)

        animator1.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {}
        })

        animator2.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                welcomeImage.setImageResource(R.drawable.mickey_welcome_new)

            }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {

            }
        })

        val set = AnimatorSet()
        set.play(animator1).before(animator2)
        set.duration = MICKEY_ROTATE_DURATION
        set.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val MICKEY_ROTATE_DURATION = 800L
    }


}