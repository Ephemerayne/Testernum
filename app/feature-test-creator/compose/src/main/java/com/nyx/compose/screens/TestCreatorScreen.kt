package com.nyx.compose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.compose.viewmodel.rememberEvent
import com.nyx.compose.views.textfields.BaseTextFieldWithBorder
import com.nyx.impl.R
import com.nyx.impl.TestCreatorViewModel
import com.nyx.impl.models.TestCreatorViewEvent

@Composable
fun TestCreatorScreen(
    textFromPrevScreen: String,
    viewModel: TestCreatorViewModel = viewModel(),
) {
    val viewState = viewModel.viewStates().observeAsState().value

    val onQuestionTextChanged =
        viewModel.rememberEvent<String, _> { TestCreatorViewEvent.QuestionTextChanged(it) }
    val onClearInputClicked =
        viewModel.rememberEvent(TestCreatorViewEvent.OnQuestionClearInputClicked)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        QuestionAndAnswersContent(
            questionInput = viewState.questionInput,
            onQuestionTextChanged = onQuestionTextChanged,
            onClearInputClicked = onClearInputClicked
        )
        NavigationButtons()
    }
}

@Composable
private fun QuestionAndAnswersContent(
    questionInput: String,
    onQuestionTextChanged: (String) -> Unit,
    onClearInputClicked: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    LazyColumn {
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.create_new_test_title)
            )

        }
        item {
            BaseTextFieldWithBorder(
                message = questionInput,
                placeholder = stringResource(R.string.question_text_field_placeholder),
                focusRequester = focusRequester,
                onTextChanged = onQuestionTextChanged,
                onClearInputClick = onClearInputClicked
            )
        }
    }
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
private fun BoxScope.NavigationButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
    ) {
        NavigationButton(
            modifier = Modifier.weight(1f),
            buttonText = stringResource(R.string.previous_question_button_text),
            onClick = {/* onPrevClick */ }
        )
        Spacer(modifier = Modifier.width(20.dp))
        NavigationButton(
            modifier = Modifier.weight(1f),
            buttonText = stringResource(R.string.next_question_button_text),
            onClick = {/* onNextClick */ }
        )
    }
}