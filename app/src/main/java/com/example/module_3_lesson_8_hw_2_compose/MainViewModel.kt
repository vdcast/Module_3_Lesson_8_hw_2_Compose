package com.example.module_3_lesson_8_hw_2_compose

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    val isStartedGame = mutableStateOf(false)
    val isReadyToCheck = mutableStateOf(false)
    val isFinishedGame = mutableStateOf(false)

    val randomNumberInt = mutableStateOf(0)

    val result = mutableStateOf(false)

    private fun getRandomNumber(): Int {
        val randomNumber = (5..20).random()
        Log.d("MYLOG", randomNumber.toString())
        return randomNumber
    }

    fun startGame() {
        viewModelScope.launch {
            val randomNumber = getRandomNumber()
            withContext(Dispatchers.Main) {
                isFinishedGame.value = false
                isStartedGame.value = true
            }
            delay(randomNumber.toLong() * 1_000)
            withContext(Dispatchers.Main) {
                randomNumberInt.value = randomNumber
                isStartedGame.value = false
                isReadyToCheck.value = true
            }
        }
    }
    fun checkNumber(guess: Int) {
        isReadyToCheck.value = false
        isFinishedGame.value = true
        result.value = guess == randomNumberInt.value
    }
}