package com.example.module_3_lesson_8_hw_2_compose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    val isStartedGame = mutableStateOf(false)
    val isReadyToCheck = mutableStateOf(false)
    val isFinishedGame = mutableStateOf(false)

    private val randomNumberInt = mutableStateOf(0)
    private val randomNumberLongDelay = mutableStateOf(0L)

    val result = mutableStateOf(false)

    private fun getRandomNumber() {
        val randomNumber = (5..20).random()
        randomNumberInt.value = randomNumber
        randomNumberLongDelay.value = randomNumber.toLong() * 1_000
    }

    suspend fun startGame() {
        getRandomNumber()
        withContext(Dispatchers.IO) {
            isFinishedGame.value = false
            isStartedGame.value = true
            delay(randomNumberLongDelay.value)
            isStartedGame.value = false
            isReadyToCheck.value = true
        }
    }

    fun finishGame(guess: Int) {
        isReadyToCheck.value = false
        isFinishedGame.value = true
        result.value = guess == randomNumberInt.value
    }
}