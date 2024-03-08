package com.devrex.networkapplication.presentation.navigation

import android.app.Activity
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface NavigationListener {

    fun registerGraph(
        activity: Activity,
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController,
        modifier: Modifier,
    )
}