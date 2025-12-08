package com.arttrip.android.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arttrip.android.core.navigation.AppNavHost
import com.arttrip.android.core.navigation.AppRoute
import com.arttrip.android.core.navigation.bottomNavItems
import com.arttrip.android.core.ui.component.bottomNav.AppBottomNavBarWithInset
import com.arttrip.android.core.ui.theme.ArtTripTheme
import com.arttrip.android.presentation.main.contract.AuthState
import com.arttrip.android.presentation.main.contract.MainState

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    uiState: MainState,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val bottomNavRoutes = remember { bottomNavItems.map { it.route }.toSet() }
    val shouldShowBottomNav = currentRoute in bottomNavRoutes

    Scaffold(
        modifier = modifier,
        containerColor = Color.White,
        bottomBar = {
            if (shouldShowBottomNav) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    AppBottomNavBarWithInset(
                        items = bottomNavItems,
                        selectedRoute = currentRoute,
                        onItemSelected = { item ->
                            navController.navigate(item.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                            }
                        },
                    )
                }
            }
        },
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            innerPadding = innerPadding,
            startDestination = AppRoute.SPLASH,
        )
    }
}

@Preview(
    name = "MainScreen Preview - LoggedIn",
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun PreviewMainScreen_LoggedIn() {
    ArtTripTheme {
        val navController = rememberNavController()
        MainScreen(
            modifier = Modifier,
            navController = navController,
            uiState =
                MainState(
                    authState = AuthState.LOGGED_IN,
                ),
        )
    }
}

@Preview(
    name = "MainScreen Preview - LoggedOut",
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun PreviewMainScreen_LoggedOut() {
    ArtTripTheme {
        val navController = rememberNavController()
        MainScreen(
            modifier = Modifier,
            navController = navController,
            uiState =
                MainState(
                    authState = AuthState.LOGGED_OUT,
                ),
        )
    }
}
