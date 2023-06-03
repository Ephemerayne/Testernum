package com.nyx.testernum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nyx.testernum.ui.theme.TesternumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TesternumTheme {
                AppNavHost()
            }
        }
    }
}