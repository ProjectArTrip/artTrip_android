package com.arttrip.android.core.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.arttrip.android.presentation.bookmark.BookmarkRoute
import com.arttrip.android.presentation.exhibition.ExhibitionDetailRoute
import com.arttrip.android.presentation.home.HomeRoute
import com.arttrip.android.presentation.home.sub.datefilter.DateFilterRoute
import com.arttrip.android.presentation.map.MapRoute
import com.arttrip.android.presentation.my.MyPageRoute
import com.arttrip.android.presentation.stamp.StampRoute

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    innerPadding: PaddingValues,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = {
            slideInHorizontally { it } + fadeIn()
        },
        exitTransition = {
            slideOutHorizontally { -it / 3 } + fadeOut()
        },
        popEnterTransition = {
            slideInHorizontally { -it / 3 } + fadeIn()
        },
        popExitTransition = {
            slideOutHorizontally { it } + fadeOut()
        },
    ) {
        // 바텀네비
        composable(BottomNavItem.Home.route) {
            HomeRoute(
                innerPadding,
                onNavigate = { targetRoute ->
                    navController.navigate(targetRoute)
                },
            )
        }
        composable(BottomNavItem.Map.route) { MapRoute(innerPadding) }
        composable(BottomNavItem.Stamp.route) { StampRoute(innerPadding) }
        composable(BottomNavItem.Bookmark.route) { BookmarkRoute(innerPadding) }
        composable(BottomNavItem.MyPage.route) {
            MyPageRoute(innerPadding, onNavigate = { targetRoute ->
                navController.navigate(targetRoute)
            })
        }

        composable(MainRoute.HOME_DATE_FILTER) {
            DateFilterRoute(
                innerPadding = innerPadding,
            )
        }

        composable(MainRoute.EXHIBITION_DETAIL, listOf(navArgument("exhibitId") { type = NavType.IntType })) { backStackEntry ->
            val exhibitId = backStackEntry.arguments?.getInt("exhibitId") ?: return@composable
            ExhibitionDetailRoute(innerPadding, exhibitId)
        }
    }
}
