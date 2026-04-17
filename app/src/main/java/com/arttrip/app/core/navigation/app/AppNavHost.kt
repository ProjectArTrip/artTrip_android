package com.arttrip.app.core.navigation.app

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arttrip.app.core.navigation.app.AppRoute
import com.arttrip.app.presentation.intro.taste.TasteRoute
import com.arttrip.app.presentation.login.LoginRoute
import com.arttrip.app.presentation.main.MainRoute
import com.arttrip.app.presentation.splash.SplashRoute

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.SPLASH,
        modifier = Modifier,
    ) {
        composable(AppRoute.SPLASH) {
            SplashRoute(
                onNavigate = { targetRoute ->
                    navController.navigate(targetRoute) {
                        popUpTo(AppRoute.SPLASH) { inclusive = true }
                    }
                },
            )
        }

        composable(AppRoute.LOGIN) {
            val systemPadding = WindowInsets.safeDrawing.asPaddingValues()

            LoginRoute(
                innerPadding = systemPadding,
                onNavigate = { targetRoute ->
                    navController.navigate(targetRoute) {
                        popUpTo(AppRoute.LOGIN) { inclusive = true }
                    }
                },
            )
        }

        composable(AppRoute.INTRO_TASTE) {
            val systemPadding = WindowInsets.safeDrawing.asPaddingValues()
            TasteRoute(
                innerPadding = systemPadding,
                onNavigate = { targetRoute ->
                    navController.navigate(targetRoute) {
                        popUpTo(AppRoute.INTRO_TASTE) { inclusive = true }
                    }
                },
            )
        }

        composable(AppRoute.MAIN) {
            MainRoute(appNavController = navController)
        }
    }
}
