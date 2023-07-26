package com.andromasters.weather.presentation

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.andromasters.weather.presentation.forecast.WeatherScreen
import com.andromasters.weather.presentation.search.SearchScreen
import com.andromasters.weather.presentation.splash.SplashScreen

enum class Screen {
    Splash,
    Search,
    Forecast,
}

@Composable
fun MainScreen() {
    fun slideOutAnimation() = slideOutHorizontally(targetOffsetX = { -1000 })
    fun slideInAnimation() = slideInHorizontally(initialOffsetX = { 1000 })
    fun fadeInAnimation() = fadeIn(animationSpec = tween(2000))
    fun fadeOutAnimation() = fadeOut(animationSpec = tween(2000))

    val navController = rememberNavController()
    val context: Context = LocalContext.current

    BackHandler { //poor resolution of finishing activity on Android 13 to run splash screen first
        (context as? Activity)?.finish()
    }

    NavHost(
        navController,
        startDestination = Screen.Splash.name,
        enterTransition = {
            slideInAnimation()
        },
        exitTransition = {
            slideOutAnimation()
        }
    ) {
        composable(route = Screen.Splash.name) {
            SplashScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.Forecast.name + "/{locationId}/{name}",
            enterTransition = {
                fadeInAnimation()
            },
            arguments = listOf(
                navArgument("locationId") {
                    type = NavType.StringType
                },
                navArgument("name") {
                    type = NavType.StringType
                }
            ),
        ) {
            WeatherScreen(
                navController = navController,
                locationId = it.arguments?.getString("locationId")!!,
                locationName = it.arguments?.getString("name")!!,
            )
        }
        composable(
            route = Screen.Search.name,
            exitTransition = {
                fadeOutAnimation()
            }
        ) {
            SearchScreen(
                navController = navController
            )
        }
    }

}