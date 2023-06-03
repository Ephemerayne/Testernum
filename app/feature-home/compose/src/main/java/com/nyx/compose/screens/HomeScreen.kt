package com.nyx.compose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.api.navigation.HomeScreenNavigation
import com.nyx.impl.models.HomeViewEvent
import com.nyx.common.viewModel.observeAction
import com.nyx.common.viewModel.rememberEvent
import com.nyx.compose.navigation.navigation
import com.nyx.impl.HomeViewModel
import com.nyx.impl.models.HomeViewAction

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
        Button(modifier = Modifier.size(200.dp), onClick = onNextScreenButtonClick) {
            Text(text = "Go to next screen")
        }
    }
}