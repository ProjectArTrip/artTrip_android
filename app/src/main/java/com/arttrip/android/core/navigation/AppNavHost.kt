package com.arttrip.android.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arttrip.android.presentation.bookmark.BookmarkRoute
import com.arttrip.android.presentation.home.HomeRoute
import com.arttrip.android.presentation.map.MapRoute
import com.arttrip.android.presentation.my.MyPageRoute
import com.arttrip.android.presentation.stamp.StampRoute

@Composable
fun AppNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier,
    ) {
        composable(BottomNavItem.Home.route) { HomeRoute(innerPadding) }
        composable(BottomNavItem.Map.route) { MapRoute(innerPadding) }
        composable(BottomNavItem.Stamp.route) { StampRoute(innerPadding) }
        composable(BottomNavItem.Bookmark.route) { BookmarkRoute(innerPadding) }
        composable(BottomNavItem.MyPage.route) { MyPageRoute(innerPadding) }
    }
}
