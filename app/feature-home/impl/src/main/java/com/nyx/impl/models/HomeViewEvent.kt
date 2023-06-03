package com.nyx.impl.models

sealed class HomeViewEvent {
    object OnButtonClicked: HomeViewEvent()
    object ActionInvoked: HomeViewEvent()
}