package com.nyx.compose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.api.navigation.HomeScreenNavigation
import com.nyx.common.viewModel.rememberEvent
import com.nyx.compose.navigation.navigation
import com.nyx.impl.HomeViewModel
import com.nyx.impl.models.HomeViewEvent

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    homeScreenNavigation: HomeScreenNavigation,
) {
    val viewState = viewModel.viewStates().observeAsState().value

    val onNextScreenButtonClick = viewModel.rememberEvent(HomeViewEvent.OnButtonClicked)

    HomeView(
        text = viewState.test,
        onNextScreenButtonClick = onNextScreenButtonClick
    )

    navigation(viewModel, homeScreenNavigation)
}

@Composable
private fun HomeView(
    text: String,
    onNextScreenButtonClick: () -> Unit,
) {
    Column() {
        Text(text)
        Button(onClick = onNextScreenButtonClick) {
            Text(text = "Go to test creator")
        }
    }
}