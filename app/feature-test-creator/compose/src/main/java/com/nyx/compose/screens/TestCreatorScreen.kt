package com.nyx.compose.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.common.viewModel.rememberEvent
import com.nyx.impl.TestCreatorViewModel
import com.nyx.impl.models.TestCreatorViewEvent

@Composable
fun TestCreatorScreen(
    textFromPrevScreen: String,
    viewModel: TestCreatorViewModel = viewModel(),
) {
    val viewState = viewModel.viewStates().observeAsState().value

    val focusRequester = remember { FocusRequester() }

    val onQuestionTextChanged =
        viewModel.rememberEvent<String, _> { TestCreatorViewEvent.QuestionTextChanged(it) }
    val onClearInputClicked =
        viewModel.rememberEvent(TestCreatorViewEvent.OnQuestionClearInputClicked)

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    text = "Создание нового теста"
                )

            }
            item {
                TextFieldView(
                    focusRequester = focusRequester,
                    message = viewState.questionInput,
                    onTextChanged = onQuestionTextChanged,
                    onClearInputClick = onClearInputClicked
                )
            }
        }
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            onClick = { }) {
            Text(text = "Следующий вопрос")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TextFieldView(
    focusRequester: FocusRequester,
    message: String,
    onTextChanged: (String) -> Unit,
    onClearInputClick: () -> Unit,
) {
    var textFieldValueState by remember {
        mutableStateOf(
            TextFieldValue(
                text = message,
                selection = TextRange(message.length)
            )
        )
    }
    val input = textFieldValueState.copy(text = message)

    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Box(modifier = Modifier.fillMaxWidth()) {
        BasicTextField(
            value = input,
            onValueChange = {
                textFieldValueState = it
                onTextChanged(it.text)
            },
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { isFocused = it.isFocused }
                .border(
                    border = if (isFocused) BorderStroke(width = 2.dp, color = Color.Black) else
                        BorderStroke(width = 1.dp, color = Color.LightGray),
                    shape = RoundedCornerShape(4.dp)
                ),
            minLines = 4,
            maxLines = 8,
            textStyle = TextStyle(
                color = Color.Red,
                fontFamily = FontFamily.SansSerif,
                fontSize = 14.sp
            ),
            cursorBrush = SolidColor(Color.Green)
        ) { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = "",
                placeholder = {
                    if (input.text.isEmpty()) {
                        Text(
                            text = "Введите вопрос",
                            color = Color.LightGray
                        )
                    }
                },
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = false,
                enabled = true,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 34.dp,
                    top = 16.dp,
                    bottom = 16.dp
                ),
            )
        }
        if (message.isNotBlank() && isFocused) {
            Image(
                modifier = Modifier
                    .padding(top = 8.dp, end = 24.dp)
                    .align(Alignment.TopEnd)
                    .clickable(onClick = onClearInputClick),
                imageVector = Icons.Default.Clear,
                contentDescription = null
            )
        }
    }
}