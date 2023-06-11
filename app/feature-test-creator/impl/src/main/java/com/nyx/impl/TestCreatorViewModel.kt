package com.nyx.impl

import com.nyx.compose.viewmodel.BaseViewModel
import com.nyx.impl.models.AnswerModel
import com.nyx.impl.models.QuestionModel
import com.nyx.impl.models.TestCreatorViewAction
import com.nyx.impl.models.TestCreatorViewEvent
import com.nyx.impl.models.TestCreatorViewState

class TestCreatorViewModel :
    BaseViewModel<TestCreatorViewState, TestCreatorViewAction, TestCreatorViewEvent>(
        initialState = TestCreatorViewState()
    ) {

    override fun obtainEvent(viewEvent: TestCreatorViewEvent) = when (viewEvent) {
        is TestCreatorViewEvent.OnQuestionTextChanged -> onQuestionTextInputChange(viewEvent.input)
        is TestCreatorViewEvent.OnQuestionClearInputClicked -> clearQuestionTextInput()
        is TestCreatorViewEvent.OnAnswerTextChanged -> onAnswerTextInputChange(
            viewEvent.answer,
            viewEvent.answerIndex
        )

        is TestCreatorViewEvent.OnAnswerClearInputClicked -> clearAnswerTextInput(viewEvent.index)
        is TestCreatorViewEvent.OnAddAnswerVariantClicked -> addNewAnswerVariant()
        is TestCreatorViewEvent.OnNextQuestionClicked -> onNextQuestionClick()
        is TestCreatorViewEvent.OnSaveTestButtonClicked -> saveTest()
        is TestCreatorViewEvent.ActionInvoked -> viewAction = null
    }

    private fun onQuestionTextInputChange(text: String) {
        viewState = viewState.copy(questionInput = text)
    }

    private fun clearQuestionTextInput() {
        viewState = viewState.copy(questionInput = "")
    }

    private fun onAnswerTextInputChange(input: String, answerIndex: Int) {
        val currentAnswer = viewState.currentQuestion.answers[answerIndex].copy(input = input)

        viewState = viewState.copy(
            currentQuestion = viewState.currentQuestion.copy(
                answers = mutableListOf<AnswerModel>().apply {
                    addAll(viewState.currentQuestion.answers)
                    set(answerIndex, currentAnswer)
                }
            )
        )
    }

    private fun clearAnswerTextInput(index: Int) {
        val currentAnswer = viewState.currentQuestion.answers[index].copy(input = "")

        viewState = viewState.copy(
            currentQuestion = viewState.currentQuestion.copy(
                answers = mutableListOf<AnswerModel>().apply {
                    addAll(viewState.currentQuestion.answers)
                    set(index, currentAnswer)
                }
            )
        )
    }

    private fun addNewAnswerVariant() {
        viewState =
            viewState.copy(
                currentQuestion = viewState.currentQuestion.copy(
                    answers = viewState.currentQuestion.answers + AnswerModel()
                )
            )
    }

    private fun onNextQuestionClick() {
        viewState = viewState.copy(
            newTest = viewState.newTest.copy(
                questions = viewState.newTest.questions + viewState.currentQuestion.copy(title = viewState.questionInput)
            ),
            currentQuestion = QuestionModel(),
            questionInput = ""
        )
    }

    private fun saveTest() {
        viewState = viewState.copy(
            newTest = viewState.newTest.copy(
                questions = viewState.newTest.questions + viewState.currentQuestion.copy(title = viewState.questionInput)
            ),
            currentQuestion = QuestionModel()
        )
        println("debug: saved test: ${viewState.newTest}")
    }
}