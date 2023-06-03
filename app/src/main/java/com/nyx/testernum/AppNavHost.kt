package com.nyx.testernum

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nyx.compose.screens.HomeScreen
import com.nyx.compose.screens.TestCreatorScreen
import com.nyx.testernum.navigation.HomeScreenNavigationImpl

private const val ARGS = "args"

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationTree.Main.Home.screenName,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        val homeScreenNavigation = HomeScreenNavigationImpl(navController)

        composable(startDestination) {
            HomeScreen(homeScreenNavigation = homeScreenNavigation)
        }

        composable(
            route = "${NavigationTree.Main.TestCreator.screenName}/{$ARGS}",
            arguments = listOf(navArgument(ARGS) { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(ARGS)
                ?.let { TestCreatorScreen(textFromPrevScreen = it) }
        }
    }
}