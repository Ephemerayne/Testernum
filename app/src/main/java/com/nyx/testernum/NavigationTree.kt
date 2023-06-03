package com.nyx.testernum

sealed class NavigationTree {
    enum class Main(val screenName: String) {
        Home("home"), TestCreator("testCreator")
    }
}