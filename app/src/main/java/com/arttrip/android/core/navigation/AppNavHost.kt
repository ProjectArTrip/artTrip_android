package com.arttrip.android.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arttrip.android.presentation.login.LoginRoute
import com.arttrip.android.presentation.bookmark.BookmarkRoute
import com.arttrip.android.presentation.home.HomeRoute
import com.arttrip.android.presentation.map.MapRoute
import com.arttrip.android.presentation.my.MyPageRoute
import com.arttrip.android.presentation.stamp.StampRoute

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    innerPadding: PaddingValues,
    startDestination: String,
    onLoginSuccess: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        // 로그인
        composable("login") {
            LoginRoute(
                innerPadding = innerPadding,
                onLoginSuccess = onLoginSuccess,
            )
        }

        // 탭들
        composable(BottomNavItem.Home.route) { HomeRoute(innerPadding) }
        composable(BottomNavItem.Map.route) { MapRoute(innerPadding) }
        composable(BottomNavItem.Stamp.route) { StampRoute(innerPadding) }
        composable(BottomNavItem.Bookmark.route) { BookmarkRoute(innerPadding) }
        composable(BottomNavItem.MyPage.route) { MyPageRoute(innerPadding) }
    }
}
