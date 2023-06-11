package com.nyx.impl.models

data class TestCreatorViewState(
    val questionInput: String = "",
    val newTest: TestModel = TestModel(),
    val currentQuestion: QuestionModel = QuestionModel()
)

data class TestModel(
    val title: String = "NEW",
    val questions: List<QuestionModel> = listOf(),
)

data class AnswerModel(
    val input: String = "",
    val points: Int = 0
)

data class QuestionModel(
    val title: String = "",
    val answers: List<AnswerModel> = listOf(AnswerModel())
)