package com.nyx.compose.navigation

import androidx.compose.runtime.Composable
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.api.navigation.HomeScreenNavigation
import com.nyx.common.viewModel.observeAction
import com.nyx.impl.HomeViewModel
import com.nyx.impl.models.HomeViewAction
import com.nyx.impl.models.HomeViewEvent

@Composable
fun navigation(
    homeViewModel: HomeViewModel,
    homeScreenNavigation: HomeScreenNavigation,
) {
    val viewState = homeViewModel.viewStates().observeAsState().value

    homeViewModel.observeAction(HomeViewEvent.ActionInvoked) { action ->
        when (action) {
            is HomeViewAction.OpenSecondScreen -> {
                homeScreenNavigation.navigateToTestCreator(viewState.test)
            }
        }
    }
}