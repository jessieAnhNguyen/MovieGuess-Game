package com.anguy39.movieguess.model

import android.util.Log
import com.google.gson.Gson
import kotlin.random.Random

private const val TAG = "Game"

data class Question(val question: String, val solutions: List<String>)

class Questions : ArrayList<Question>()

class Game (jsonString: String) {
    private var questions = ArrayList<Question>()

    lateinit var currentQuestion: Question
    var currentQuestionIndex = 0
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
//        Log.d(TAG, "Game: character is " + characterSelection)
    }


    fun newQuestion() {
        if (answers.size < NUM_QUESTIONS) {
            currentQuestionIndex = Random.nextInt(questions.size)
            currentQuestion = questions[currentQuestionIndex]
            val temp = currentQuestion.solutions.toMutableList().apply {
                shuffle()
            }
            solutions = temp
        } else {
            // start new game
            answers.clear()
        }
    }

    fun getQuestionList(): ArrayList<Question> {
        return questions
    }

    fun getCurrentQuestion(): Int {
        return currentQuestionIndex
    }

    companion object {
        const val NUM_QUESTIONS = 4
    }

}