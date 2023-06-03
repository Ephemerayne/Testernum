package com.nyx.testernum.navigation

import androidx.navigation.NavController
import com.nyx.api.navigation.HomeScreenNavigation
import com.nyx.testernum.NavigationTree

class HomeScreenNavigationImpl(
    private val navController: NavController
): HomeScreenNavigation {

    override fun navigateToTestCreator(args: Any) {
        navController.navigate("${NavigationTree.Main.TestCreator.screenName}/${args}")
    }
}