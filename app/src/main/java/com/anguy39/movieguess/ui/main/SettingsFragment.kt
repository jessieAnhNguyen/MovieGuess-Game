package com.anguy39.movieguess.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.anguy39.movieguess.R
import com.anguy39.movieguess.databinding.FragmentConfigBinding
import com.anguy39.movieguess.databinding.FragmentSettingsBinding
import com.anguy39.movieguess.ui.main.game.GameViewModel

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: GameViewModel by activityViewModels()
    private var characterSelection = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        binding.run {
            vm = sharedViewModel
            lifecycleOwner = this@SettingsFragment
        }

        binding.characterRadioGroup.setOnCheckedChangeListener { group, id ->
            val radio: RadioButton = group.findViewById(id)
            characterSelection = group.indexOfChild(radio)
        }

        binding.backtoWelcomeButton.setOnClickListener {
            if (characterSelection >= 0) {
                sharedViewModel.updateCharacter(characterSelection)
                it.findNavController().navigate(R.id.action_settingsFragment_to_welcomeFragment)
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}