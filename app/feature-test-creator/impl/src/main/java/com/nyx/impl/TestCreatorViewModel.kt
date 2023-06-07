package com.nyx.impl

import com.nyx.compose.viewmodel.BaseViewModel
import com.nyx.impl.models.TestCreatorViewAction
import com.nyx.impl.models.TestCreatorViewEvent
import com.nyx.impl.models.TestCreatorViewState

class TestCreatorViewModel :
    BaseViewModel<TestCreatorViewState, TestCreatorViewAction, TestCreatorViewEvent>(
        initialState = TestCreatorViewState()
    ) {

    override fun obtainEvent(viewEvent: TestCreatorViewEvent) = when (viewEvent) {
        is TestCreatorViewEvent.QuestionTextChanged -> onQuestionTextInputChange(viewEvent.input)
        is TestCreatorViewEvent.OnQuestionClearInputClicked -> clearQuestionTextInput()
    }

    private fun onQuestionTextInputChange(text: String) {
        viewState = viewState.copy(questionInput = text)
    }

    private fun clearQuestionTextInput() {
        viewState = viewState.copy(questionInput = "")
    }
}