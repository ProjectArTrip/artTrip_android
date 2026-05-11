package com.arttrip.app.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arttrip.app.core.navigation.main.BottomNavItem
import com.arttrip.app.core.navigation.main.MainNavHost
import com.arttrip.app.core.navigation.main.bottomNavItems
import com.arttrip.app.core.ui.component.bottomNav.AppBottomNavBarWithInset
import com.arttrip.app.core.ui.theme.ArtTripTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    hasUnread: Boolean = false,
    onRefreshHasUnread: () -> Unit = {},
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val bottomNavRoutes =
        listOf(
            BottomNavItem.Home.route,
            BottomNavItem.Map.route,
            BottomNavItem.Stamp.route,
            BottomNavItem.Bookmark.route,
            BottomNavItem.MyPage.route,
        )

    val isBottomNavVisible = currentRoute in bottomNavRoutes

    Scaffold(
        modifier = modifier,
        containerColor = Color.White,
        bottomBar = {
            if (isBottomNavVisible) {
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
        MainNavHost(
            navController = navController,
            innerPadding = innerPadding,
            startDestination = BottomNavItem.Home.route,
            hasUnread = hasUnread,
            onRefreshHasUnread = onRefreshHasUnread,
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
        )
    }
}
