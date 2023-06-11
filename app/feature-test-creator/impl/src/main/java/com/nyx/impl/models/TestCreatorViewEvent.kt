package com.nyx.impl.models

sealed class TestCreatorViewEvent {
    data class OnQuestionTextChanged(val input: String): TestCreatorViewEvent()
    object OnQuestionClearInputClicked: TestCreatorViewEvent()
    data class OnAnswerTextChanged(val answer: String, val answerIndex: Int): TestCreatorViewEvent()
    data class OnAnswerClearInputClicked(val index: Int): TestCreatorViewEvent()
    object OnAddAnswerVariantClicked: TestCreatorViewEvent()
    object OnNextQuestionClicked: TestCreatorViewEvent()
    object OnSaveTestButtonClicked: TestCreatorViewEvent()
    object ActionInvoked: TestCreatorViewEvent()
}