package com.nyx.compose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun TestCreatorScreen(
    textFromPrevScreen: String
) {
    Column {
        Text("THIS IS TEST CREATOR")
        Text("text from previous screen: $textFromPrevScreen")
    }
}