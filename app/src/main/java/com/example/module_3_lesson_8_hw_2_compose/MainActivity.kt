package com.example.module_3_lesson_8_hw_2_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.module_3_lesson_8_hw_2_compose.ui.theme.Module_3_Lesson_8_hw_2_ComposeTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.module_3_lesson_8_hw_2_compose.ui.theme.Cyan10
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Module_3_Lesson_8_hw_2_ComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(
    viewModelMain: MainViewModel = viewModel()
) {
    var inputNumber by remember { mutableStateOf("") }
    var isInputEmpty by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val correctNumber by viewModelMain.randomNumberInt
    val isStartedGame by viewModelMain.isStartedGame
    val isReadyToCheck by viewModelMain.isReadyToCheck
    val isFinishedGame by viewModelMain.isFinishedGame
    val result by viewModelMain.result


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cyan10),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.text_title),
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = FontFamily.Monospace
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = dimensionResource(id = R.dimen.padding_medium)),
            text = stringResource(id = R.string.text_description),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Monospace
        )

        AnimatedVisibility(visible = !isStartedGame && !isReadyToCheck && !isFinishedGame) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(vertical = dimensionResource(id = R.dimen.padding_medium)),
                onClick = {
                    viewModelMain.startGame()
                })
            { Text(text = stringResource(id = R.string.button_start))}
        }
        AnimatedVisibility(visible = isStartedGame && !isReadyToCheck && !isFinishedGame) {
            CircularProgressIndicator(
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
            )
        }
        AnimatedVisibility(visible = !isStartedGame && isReadyToCheck && !isFinishedGame) {
            Column(
                modifier = Modifier.fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = inputNumber,
                    onValueChange = {
                        inputNumber = it

                        isInputEmpty = it.isEmpty()
                    },
                    label = { Text(stringResource(id = R.string.your_guess)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    isError = isInputEmpty
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.padding_medium)),
                    onClick = {
                        viewModelMain.checkNumber(inputNumber.toInt())
                    },
                    enabled = inputNumber.isNotEmpty()
                )
                { Text(text = stringResource(id = R.string.button_check))}
            }
        }
        AnimatedVisibility(visible = !isStartedGame && !isReadyToCheck && isFinishedGame) {
            Column(
                modifier = Modifier.fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.result_of_game, if (result) "won! :)" else "lost."),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(id = R.string.your_answer, inputNumber),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(id = R.string.correct_number, correctNumber),
                    style = MaterialTheme.typography.titleMedium
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.padding_medium)),
                    onClick = {
                        inputNumber = ""
                        viewModelMain.startGame()
                    })
                { Text(text = stringResource(id = R.string.button_try_again))}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Module_3_Lesson_8_hw_2_ComposeTheme {
        MyApp()
    }
}