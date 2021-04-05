package com.anguy39.movieguess.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anguy39.movieguess.R
import com.anguy39.movieguess.databinding.FragmentConfigBinding
import com.anguy39.movieguess.databinding.FragmentWelcomeBinding
import com.anguy39.movieguess.model.Level
import com.anguy39.movieguess.ui.main.game.GameViewModel

private const val TAG = "ConfigFragment"
class ConfigFragment : Fragment() {

    private var _binding: FragmentConfigBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_config, container, false)
        binding.run {
            vm = sharedViewModel
            lifecycleOwner = this@ConfigFragment
        }

        binding.configRecyclerView.layoutManager = LinearLayoutManager(context)

        binding.startGameButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_configFragment_to_gameFragment)
        }

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel.levelList.observe(viewLifecycleOwner, {
            binding.configRecyclerView.adapter = LevelAdapter(it)
        })

    }
    


    private inner class LevelViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val levelNameTextView: TextView = itemView.findViewById(R.id.level_textView)
        private var cardItem: CardView = view.findViewById(R.id.item_cardView)

        private lateinit var level: Level

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            cardItem.setCardBackgroundColor(android.graphics.Color.parseColor("#14a5c9"))
            sharedViewModel.updateLevel(level)
            configLevel = level
            sharedViewModel.newGame()
            Log.d(TAG, "call new game!")
//            Log.d(TAG, "level is " + sharedViewModel.level)
        }

        fun bind(level: Level) {
            this.level = level
            levelNameTextView.text = level.levelName
        }
    }

    private inner class LevelAdapter(private val list: List<Level>) : RecyclerView.Adapter<LevelViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
            val view = layoutInflater.inflate(R.layout.recycler_item, parent, false)
            return LevelViewHolder(view)
        }

        override fun getItemCount() = list.size

        override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
            holder.bind(list[position])
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var configLevel = Level(0, "Easy")
    }


}