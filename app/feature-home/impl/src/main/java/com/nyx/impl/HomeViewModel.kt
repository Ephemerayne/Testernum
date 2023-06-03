package com.nyx.impl

import com.nyx.impl.models.HomeViewEvent
import com.nyx.common.viewModel.BaseViewModel
import com.nyx.impl.models.HomeViewState
import com.nyx.impl.models.HomeViewAction

class HomeViewModel: BaseViewModel<HomeViewState, HomeViewAction, HomeViewEvent>(
    initialState = HomeViewState()
) {

    override fun obtainEvent(viewEvent: HomeViewEvent) = when (viewEvent) {
        HomeViewEvent.OnButtonClicked -> viewAction = HomeViewAction.OpenSecondScreen(viewState.test)
        HomeViewEvent.ActionInvoked -> viewAction = null
    }
}