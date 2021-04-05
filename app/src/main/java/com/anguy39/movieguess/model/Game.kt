package com.anguy39.movieguess.model

import android.util.Log
import com.google.gson.Gson
import kotlin.random.Random

private const val TAG = "Game"

data class Question(val question: String, val solutions: List<String>)
data class GameLevel(val level: String, val list: Questions)

class Questions : ArrayList<Question>()
class GameLevels: ArrayList<GameLevel>()

class Game (jsonString: String, level: Int) {
    private var questions = ArrayList<Question>()

    lateinit var gameLevels: ArrayList<GameLevel>

    lateinit var currentQuestion: Question
    var currentQuestionIndex = 0
    lateinit var solutions: List<String>
    val answers: MutableList<Boolean> = mutableListOf()

    var character = 0

    init {
        val gson = Gson()
        gameLevels = gson.fromJson(jsonString, GameLevels::class.java)

        var currLevel = gameLevels[0]
        for (i in 0 until gameLevels.size) {
            if (gameLevels[i].level == matchLevel(level)) {
                currLevel = gameLevels[i]
                break
            }
        }
        questions = currLevel.list
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

    fun matchLevel (level: Int): String {
        when (level) {
            0 -> return "easy"
            1 -> return "medium"
            2 -> return "hard"
        }
        return ""
    }

    companion object {
        const val NUM_QUESTIONS = 4
    }

}