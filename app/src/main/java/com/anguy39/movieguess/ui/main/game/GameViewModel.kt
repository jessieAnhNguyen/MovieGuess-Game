package com.anguy39.movieguess.ui.main.game

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anguy39.movieguess.model.Game
import com.anguy39.movieguess.model.Level
import com.anguy39.movieguess.ui.main.ConfigFragment
import com.anguy39.movieguess.ui.main.SettingsFragment

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

    private var _level = MutableLiveData<Level>()
    var level: LiveData<Level> = _level

    private val _levelList = MutableLiveData<List<Level>>()
    var levelList: LiveData<List<Level>> = _levelList


    private var easyJsonString = ""
    private var mediumJsonString = ""
    private var hardJsonString = ""

    private var jsonString = ""

    init {
//        easyJsonString = app.assets.open("moviesEasy.json").bufferedReader().use { it.readText() }
//        mediumJsonString = app.assets.open("moviesEasy.json").bufferedReader().use { it.readText() }
//        hardJsonString = app.assets.open("moviesEasy.json").bufferedReader().use { it.readText() }

        val easyLevel = Level(0, "Easy")
        val mediumLevel = Level(1, "Medium")
        val hardLevel = Level(2, "Hard")
        _levelList.value = listOf(easyLevel, mediumLevel, hardLevel)

        _level.value = ConfigFragment.configLevel

        Log.d(TAG, "In init, the level is " + _level.value?.LevelId)

        jsonString = app.assets.open("moviesEasy.json").bufferedReader().use { it.readText() }
        newGame()
//        Log.d(TAG, "current level is " + game.level)

    }

//    fun newLevel():String {
//        when (level.LevelId) {
//            0 -> return easyJsonString
//            1 -> return mediumJsonString
//            2 -> return hardJsonString
//        }
//        return easyJsonString
//    }

    fun newGame() {
        Log.d(TAG, "Initialize new game ...")
        this.game = Game(jsonString, _level.value!!)

        Log.d(TAG, "new level is " + _level.value?.LevelId)

        Log.d(TAG, "First game is " + this.game)
        newQuestion(false)
    }

    fun newQuestion(wasGameOverPressed: Boolean) {
        Log.d(TAG, "game level is " + _level.value?.LevelId)
        Log.d(TAG, "Then game is " + game)
        game.newQuestion(wasGameOverPressed)
        _question.value = game.currentQuestion.question
        _solutions.value = game.solutions
        Log.d(TAG, "new question is: " + question.value)
    }

    fun answerQuestion(index: Int) {
        game.answer(index)
        _answers.value = game.answers.toBooleanArray()
    }

    fun updateCharacter(characterSelection: Int) {
        game.updateCharacter(characterSelection)

    }

    fun updateLevel(levelSelection: Level) {
        _level.value = levelSelection
        game.updateLevel(levelSelection.LevelId)
    }

//    fun updateLevel(levelSelection: Int) {
//        game.updateLevel(levelSelection)
//        _level.value = game.level
//    }

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

    fun resetGame() {
        game.resetGame()
    }


}