package com.anguy39.movieguess.model

import android.util.Log
import com.google.gson.Gson
import kotlin.random.Random


data class Question(val question: String, val solutions: List<String>)

class Questions : ArrayList<Question>()

class Game (jsonString: String) {
    private var questions = ArrayList<Question>()

    lateinit var currentQuestion: Question
    lateinit var solutions: List<String>
    val answers: MutableList<Boolean> = mutableListOf()

    var character = 0

    init {
        val gson = Gson()
        questions = gson.fromJson(jsonString, Questions::class.java)
        Log.d("Questions: ", questions.toString())
    }

    fun answer(answer: Int) {
        answers.add(solutions[answer] == currentQuestion.solutions.last())
    }

    fun updateCharacter(characterSelection: Int) {
        character = characterSelection
    }

    fun newQuestion() {
        if (answers.size < NUM_QUESTIONS) {
            currentQuestion = questions[Random.nextInt(questions.size)]
            val temp = currentQuestion.solutions.toMutableList().apply {
                shuffle()
            }
            solutions = temp
        } else {
            // start new game
            answers.clear()
        }
    }

    companion object {
        const val NUM_QUESTIONS = 4
    }

}