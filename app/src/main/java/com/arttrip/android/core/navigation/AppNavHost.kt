package com.arttrip.android.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arttrip.android.presentation.intro.IntroRoute
import com.arttrip.android.presentation.login.LoginRoute
import com.arttrip.android.presentation.main.MainRoute
import com.arttrip.android.presentation.splash.SplashRoute

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
            LoginRoute(
                innerPadding = PaddingValues(),
                onNavigate = { targetRoute ->
                    navController.navigate(targetRoute) {
                        popUpTo(AppRoute.LOGIN) { inclusive = true }
                    }
                },
            )
        }

        composable(AppRoute.INTRO) {
            IntroRoute(
                innerPadding = PaddingValues(),
                onNavigate = { targetRoute ->
                    navController.navigate(targetRoute) {
                        popUpTo(AppRoute.INTRO) { inclusive = true }
                    }
                },
            )
        }

        // 여기서부터가 "메인 앱" 진입
        composable(AppRoute.MAIN) {
            MainRoute(appNavController = navController)
        }
    }
}
