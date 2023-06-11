package com.nyx.compose.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.compose.viewmodel.rememberEvent
import com.nyx.compose.views.textfields.BaseTextFieldWithBorder
import com.nyx.impl.R
import com.nyx.impl.TestCreatorViewModel
import com.nyx.impl.models.TestCreatorViewEvent
import com.nyx.impl.models.TestCreatorViewState

@Composable
fun TestCreatorScreen(
    textFromPrevScreen: String,
    viewModel: TestCreatorViewModel = viewModel(),
) {
    val viewState = viewModel.viewStates().observeAsState().value

    val onQuestionTextChanged =
        viewModel.rememberEvent<String, _> { TestCreatorViewEvent.OnQuestionTextChanged(it) }
    val onClearQuestionInputClick =
        viewModel.rememberEvent(TestCreatorViewEvent.OnQuestionClearInputClicked)
    val onAnswerTextChanged =
        viewModel.rememberEvent<String, Int, _> { input, index ->
            TestCreatorViewEvent.OnAnswerTextChanged(input, index)
        }
    val onClearAnswerInputClick =
        viewModel.rememberEvent<Int, _> { TestCreatorViewEvent.OnAnswerClearInputClicked(it) }
    val onAddAnswerVariantClick =
        viewModel.rememberEvent(TestCreatorViewEvent.OnAddAnswerVariantClicked)
    val onNextQuestionButtonClick =
        viewModel.rememberEvent(TestCreatorViewEvent.OnNextQuestionClicked)
    val onSaveTestButtonClick =
        viewModel.rememberEvent(TestCreatorViewEvent.OnSaveTestButtonClicked)

    var navigationButtonsHeight by remember { mutableStateOf(0.dp) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        QuestionAndAnswersContent(
            viewState = viewState,
            onQuestionTextChanged = onQuestionTextChanged,
            onClearQuestionInputClicked = onClearQuestionInputClick,
            onAnswerTextChanged = onAnswerTextChanged,
            onClearAnswerInputClicked = onClearAnswerInputClick,
            onAddAnswerVariantClick = onAddAnswerVariantClick,
            onSaveTestButtonClick = onSaveTestButtonClick,
            paddingDp = navigationButtonsHeight
        )
        NavigationButtons(
            onGetHeight = {
                navigationButtonsHeight = it
            },
            onPreviousQuestionButtonClick = {},
            onNextQuestionButtonClick = onNextQuestionButtonClick
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun QuestionAndAnswersContent(
    viewState: TestCreatorViewState,
    onQuestionTextChanged: (String) -> Unit,
    onClearQuestionInputClicked: () -> Unit,
    onAnswerTextChanged: (input: String, index: Int) -> Unit,
    onClearAnswerInputClicked: (index: Int) -> Unit,
    onAddAnswerVariantClick: () -> Unit,
    onSaveTestButtonClick: () -> Unit,
    paddingDp: Dp,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        LazyColumn {
            item {
                TitleView(text = stringResource(id = R.string.create_new_test_title))
            }
            item {
                BaseTextFieldWithBorder(
                    message = viewState.questionInput,
                    placeholder = stringResource(R.string.question_text_field_placeholder),
                    focusRequester = focusRequester,
                    onTextChanged = onQuestionTextChanged,
                    onClearInputClick = onClearQuestionInputClicked
                )
            }
            item {
                TitleView(text = stringResource(id = R.string.answer_variants_title))
            }

            //TODO request focus when added new answer
            itemsIndexed(viewState.currentQuestion.answers) { index, answer ->
                BaseTextFieldWithBorder(
                    message = answer.input,
                    minLines = 1,
                    maxLines = 1,
                    placeholder = "Введите ответ",
                    isSingleLine = true,
                    focusRequester = focusRequester,
                    onTextChanged = { onAnswerTextChanged(it, index) },
                    onClearInputClick = { onClearAnswerInputClicked(index) }
                )
                Spacer(modifier = Modifier.height(12.dp)) // TODO make a vertical spacer view
            }
            item {
                AddButton(onClick = onAddAnswerVariantClick)
            }
            item {
                Spacer(modifier = Modifier.height(12.dp)) // TODO make a vertical spacer view
            }
            item {
                Button(
                    modifier = Modifier
                        .padding(bottom = paddingDp),
                    onClick = onSaveTestButtonClick,
                ) {
                    Text(text = "Сохранить")
                }
            }
        }
    }
}

@Composable
private fun TitleView(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        textAlign = TextAlign.Center,
        text = text
    )
}

@Composable
private fun NavigationButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .padding(vertical = 16.dp),
        onClick = onClick
    ) {
        Text(
            text = buttonText,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BoxScope.NavigationButtons(
    onGetHeight: (height: Dp) -> Unit,
    onPreviousQuestionButtonClick: () -> Unit,
    onNextQuestionButtonClick: () -> Unit,

    ) {
    val localDensity = LocalDensity.current
    var navButtonsHeight by remember { mutableStateOf(0.dp) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .onGloballyPositioned { coordinates ->
                navButtonsHeight = with(localDensity) { coordinates.size.height.toDp() }
                onGetHeight(navButtonsHeight)
            }
    ) {
        NavigationButton(
            modifier = Modifier.weight(1f),
            buttonText = stringResource(R.string.previous_question_button_text),
            onClick = onPreviousQuestionButtonClick
        )
        Spacer(modifier = Modifier.width(20.dp))  // TODO make a horizontal spacer view
        NavigationButton(
            modifier = Modifier.weight(1f),
            buttonText = stringResource(R.string.next_question_button_text),
            onClick = onNextQuestionButtonClick
        )
    }
}

@Composable
private fun AddButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(
        modifier = modifier.size(40.dp),
        shape = CircleShape,
        border = BorderStroke(1.dp, Color.Blue),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue),
        onClick = onClick
    ) {
        Icon(Icons.Default.Add, contentDescription = "content description")
    }
}