package com.nyx.impl

import com.nyx.compose.viewmodel.BaseViewModel
import com.nyx.impl.models.HomeViewAction
import com.nyx.impl.models.HomeViewEvent
import com.nyx.impl.models.HomeViewState

class HomeViewModel: BaseViewModel<HomeViewState, HomeViewAction, HomeViewEvent>(
    initialState = HomeViewState()
) {

    override fun obtainEvent(viewEvent: HomeViewEvent) = when (viewEvent) {
        HomeViewEvent.OnButtonClicked -> viewAction = HomeViewAction.OpenSecondScreen(viewState.test)
        HomeViewEvent.ActionInvoked -> viewAction = null
    }
}