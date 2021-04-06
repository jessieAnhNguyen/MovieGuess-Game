package com.anguy39.movieguess.model

import android.util.Log
import com.anguy39.movieguess.ui.main.game.GameViewModel
import com.google.gson.Gson
import kotlin.random.Random

private const val TAG = "Game"

data class Question(val question: String, val solutions: List<String>)
data class GameLevel(val level: String, val list: Questions)

class Questions : ArrayList<Question>()
class GameLevels: ArrayList<GameLevel>()
data class Level (var LevelId: Int, var levelName: String)

class Game (jsonString: String, level: Level) {
    private var questions = ArrayList<Question>()

    lateinit var gameLevels: ArrayList<GameLevel>

    lateinit var currentQuestion: Question
    var currentQuestionIndex = 0
    lateinit var solutions: List<String>
    val answers: MutableList<Boolean> = mutableListOf()

    var character = 0
    val gson = Gson()
    var gameLevel = 0

    init {

        gameLevels = gson.fromJson(jsonString, GameLevels::class.java)

        var currLevel = gameLevels[0]
        Log.d(TAG, "hello: level is " + level)

        gameLevel = level.LevelId
        Log.d(TAG, "AAAAAAHHH why is this being created")
        for (i in 0 until gameLevels.size) {
            if (gameLevels[i].level == matchLevel(level.LevelId)) {
                currLevel = gameLevels[i]
                break
            }
        }
        questions = currLevel.list
//        Log.d("Questions: ", questions.toString())
    }

    fun answer(answer: Int) {
        answers.add(solutions[answer] == currentQuestion.solutions.last())
    }

    fun updateCharacter(characterSelection: Int) {
        character = characterSelection
//        Log.d(TAG, "Game: character is " + characterSelection)
    }

    fun updateLevel(levelSelection: Int) {
        gameLevel = levelSelection
        Log.d(TAG, "Game: level is " + gameLevel)
    }


    fun newQuestion(wasGameOverPressed: Boolean) {
        Log.d(TAG, "ARRAY SIZE = " + answers.size)
        if(wasGameOverPressed){
            answers.clear()
            return
        }
        if (answers.size < NUM_QUESTIONS) {
            Log.d(TAG, "not new answer here")
            currentQuestionIndex = Random.nextInt(questions.size)
            currentQuestion = questions[currentQuestionIndex]
            val temp = currentQuestion.solutions.toMutableList().apply {
                shuffle()
            }
            solutions = temp
        } else {
            // start new game
            Log.d(TAG, "clear answers here")
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

    fun resetGame () {
        gameLevel = 0
        var currLevel = gameLevels[0]
        for (i in 0 until gameLevels.size) {
            if (gameLevels[i].level == matchLevel(gameLevel)) {
                currLevel = gameLevels[i]
                break
            }
        }
        questions = currLevel.list
        newQuestion(false)
//        answers.clear()
        Log.d(TAG, "HELLLLLLLO answers size is "+ answers.size)
    }

    companion object {
        const val NUM_QUESTIONS = 4
    }

}