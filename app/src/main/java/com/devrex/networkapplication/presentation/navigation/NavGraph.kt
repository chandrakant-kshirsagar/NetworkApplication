package com.devrex.networkapplication.presentation.navigation


import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    activity: Activity,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AxisDepositNavigator.defaultRoute()
    ) {
        register(
            activity,
            AxisDepositNavigator,
            navController,
            modifier
        )
    }
}