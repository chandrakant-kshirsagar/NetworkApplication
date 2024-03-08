package com.devrex.networkapplication.presentation.navigation

import android.app.Activity
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

fun NavGraphBuilder.register(
    activity: Activity,
    navigationListener: NavigationListener,
    navHostController: NavHostController,
    modifier: Modifier,
) {
    navigationListener.registerGraph(
        activity,
        this,
        navHostController,
        modifier
    )

}