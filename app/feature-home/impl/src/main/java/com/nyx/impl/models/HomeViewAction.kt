package com.nyx.impl.models

sealed class HomeViewAction {
    data class OpenSecondScreen(val text: String): HomeViewAction()
}