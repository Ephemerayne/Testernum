package com.nyx.impl.models

sealed class TestCreatorViewAction {
    object OpenNextQuestionScreen: TestCreatorViewAction()
    object OpenPreviousQuestionScreen: TestCreatorViewAction()
}