package com.devrex.networkapplication.presentation.navigation

import android.app.Activity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.devrex.networkapplication.presentation.detail_page.DetailsPage
import com.devrex.networkapplication.presentation.detail_page.DetailsPageViewModel
import com.devrex.networkapplication.presentation.movie.MovieList
import com.devrex.networkapplication.presentation.movie.MovieViewModel

object AxisDepositNavigator : NavigationListener {

    private const val defaultRoute = "defaultRoute"
    private const val movieList = "movie_list"
    private const val movieDetails = "movie_details"
    private const val movieId = "movie_id"

    fun defaultRoute() = defaultRoute
    fun movieDetailsRoute(movieId: String) = "$movieDetails/${movieId}"

    @OptIn(ExperimentalAnimationApi::class)
    override fun registerGraph(
        activity: Activity,
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController,
        modifier: Modifier,
    ) {
        navGraphBuilder.navigation(
            route = defaultRoute,
            startDestination = movieList,
            enterTransition = {
                enterAnimation()
            },
            exitTransition = {
                exitAnimation()
            },
            popEnterTransition = {
                popEnterAnimation()
            },
            popExitTransition = {
                popExitAnimation()
            }
        ) {
            composable(
                route = movieList,
            ) {
                val vm: MovieViewModel = viewModel()
                MovieList(vm, navHostController)
            }
            composable(
                route = "$movieDetails/{$movieId}",
                arguments = listOf(
                    navArgument(movieId) { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val movieId = arguments.getString(movieId)
                val factory = viewModelFactory {
                    initializer { DetailsPageViewModel(movieId = movieId) }
                }
                val vm: DetailsPageViewModel = viewModel(factory = factory)
                DetailsPage(vm)
            }
        }
    }

    private fun enterAnimation(): EnterTransition {
        return slideInHorizontally(
            initialOffsetX = { 1800 },
            animationSpec = tween(
                500,
                easing = LinearEasing
            )
        )
    }

    private fun exitAnimation(): ExitTransition {
        return slideOutHorizontally(
            targetOffsetX = { -1800 },
            animationSpec = tween(
                500,
                easing = LinearEasing
            )
        )
    }

    private fun popEnterAnimation(): EnterTransition {
        return slideInHorizontally(
            initialOffsetX = { -1800 },
            animationSpec = tween(
                500,
                easing = LinearEasing
            )
        )
    }

    private fun popExitAnimation(): ExitTransition {
        return slideOutHorizontally(
            targetOffsetX = { 1800 },
            animationSpec = tween(
                500,
                easing = LinearEasing
            )
        )
    }
}