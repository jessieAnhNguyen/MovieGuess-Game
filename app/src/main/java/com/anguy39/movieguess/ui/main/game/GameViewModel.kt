package com.anguy39.movieguess.ui.main.game

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anguy39.movieguess.model.Game

private const val TAG = "GameViewModel"
class GameViewModel(app: Application) : AndroidViewModel(app) {

    private lateinit var game: Game

    private var _question = MutableLiveData<String>()
    var question: LiveData<String> = _question

    private var _solutions = MutableLiveData<List<String>>()
    var solutions: LiveData<List<String>> = _solutions

    private var _answers = MutableLiveData<BooleanArray>()
    var answers: LiveData<BooleanArray> = _answers

    private var _characters = MutableLiveData<List<String>>(listOf("Olaf", "Maleficent", "Cinderella"))
    var characters: LiveData<List<String>> = _characters

    private var _character = MutableLiveData<Int>()
    var character: LiveData<Int> = _character

    private var level = 0

    private val _levelList = MutableLiveData<List<Level>>()
    var levelList: LiveData<List<Level>> = _levelList

    data class Level (val LevelId: Int, var levelName: String)

    init {
        val jsonString = app.assets.open("moviesEasy.json").bufferedReader().use { it.readText() }
        game = Game(jsonString)
//        game.questions
        newQuestion()

        val easyLevel = Level(0, "Easy")
        val mediumLevel = Level(1, "Medium")
        val hardLevel = Level(2, "Hard")
        _levelList.value = listOf(easyLevel, mediumLevel, hardLevel)

        Log.d(TAG, "level is " + level)
    }

    fun newQuestion() {
        game.newQuestion()
        _question.value = game.currentQuestion.question
        _solutions.value = game.solutions
    }

    fun answerQuestion(index: Int) {
        game.answer(index)
        _answers.value = game.answers.toBooleanArray()
    }

    fun updateCharacter(characterSelection: Int) {
        game.updateCharacter(characterSelection)

    }

    fun getCharacter() : Int {
        return game.character
    }

    fun getQuestionImage(imgIndex: Int): String {
        var questionList = game.getQuestionList()
        return questionList[imgIndex].question
    }

    fun getCurrentQuestion(): Int {
        return game.getCurrentQuestion()
    }

    fun setLevel(level: Int) {
        this.level = level
    }
}