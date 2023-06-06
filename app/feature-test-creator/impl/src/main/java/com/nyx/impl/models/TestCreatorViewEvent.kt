package com.nyx.impl.models

sealed class TestCreatorViewEvent {
    data class QuestionTextChanged(val input: String): TestCreatorViewEvent()
    object OnQuestionClearInputClicked: TestCreatorViewEvent()
}