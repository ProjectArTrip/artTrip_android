package com.arttrip.android.core.navigation.main

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.navigation.mypage.MyPageNavHost
import com.arttrip.android.presentation.bookmark.BookmarkRoute
import com.arttrip.android.presentation.exhibition.ExhibitionDetailRoute
import com.arttrip.android.presentation.home.HomeRoute
import com.arttrip.android.presentation.home.sub.dateresult.DateResultRoute
import com.arttrip.android.presentation.home.sub.notification.NotificationRoute
import com.arttrip.android.presentation.home.sub.region.RegionRoute
import com.arttrip.android.presentation.home.sub.search.SearchRoute
import com.arttrip.android.presentation.map.MapRoute
import com.arttrip.android.presentation.my.sub.taste.TasteRoute
import com.arttrip.android.presentation.reviewwrite.ReviewWriteRoute
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
                onNavigateNotification = navController::navigateToNotification,
                onNavigateDateFilter = navController::navigateToDateFilter,
                onNavigateSearch = navController::navigateToSearch,
                onNavigateExhibitionDetail = { id ->
                    navController.navigateToExhibitionDetail(id)
                },
                onNavigateRegion = { region ->
                    navController.navigateToRegion(region)
                }

            )
        }
        composable(BottomNavItem.Map.route) { MapRoute(innerPadding) }
        composable(BottomNavItem.Stamp.route) { StampRoute(innerPadding) }
        composable(BottomNavItem.Bookmark.route) {
            BookmarkRoute(
                innerPadding,
                onNavigateExhibitionDetail = { exhibitId ->
                    navController.navigateToExhibitionDetail(exhibitId)
                },
            )
        }
        composable(BottomNavItem.MyPage.route) {
            val myPageNavController = rememberNavController()

            MyPageNavHost(
                navController = myPageNavController,
                mainNavController = navController,
                innerPadding = innerPadding,
            )
        }

        composable(MainRoute.HOME_DATE_RESULT) {
            DateResultRoute(
                innerPadding = innerPadding,
            )
        }
        composable(MainRoute.HOME_NOTIFICATION) {
            NotificationRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack
            )
        }

        composable(MainRoute.HOME_NOTIFICATION) {
            NotificationRoute(
                innerPadding = innerPadding
            )
        }

        composable(MainRoute.HOME_SEARCH) {
            SearchRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack
            )
        }

        composable(MainRoute.HOME_REGION, listOf(navArgument("region") {type = NavType.EnumType(
            DomesticRegion::class.java)})) { backStackEntry ->
            val region = backStackEntry.arguments?.getSerializable("region") as? DomesticRegion ?: return@composable
            RegionRoute(
                innerPadding = innerPadding,
                region = region
            )
        }

        composable(MainRoute.EXHIBITION_DETAIL, listOf(navArgument("exhibitId") { type = NavType.IntType })) { backStackEntry ->
            val exhibitId = backStackEntry.arguments?.getInt("exhibitId") ?: return@composable
            ExhibitionDetailRoute(
                innerPadding,
                exhibitId,
                onBack = navController::popBackStack,
                onNavigateReviewWrite = { prefill ->
                    navController.navigateToReviewWrite(
                        exhibitId,
                        prefill,
                    )
                },
            )
        }

        composable(
            route = MainRoute.REVIEW_WRITE,
        ) { _ ->
            val prefill = remember { navController.consumeReviewWritePrefill() }

            ReviewWriteRoute(
                innerPadding = innerPadding,
                prefill = prefill,
                onBack = navController::popBackStack,
            )
        }

        composable(route = MainRoute.TASTE_ANALYSIS) {
            TasteRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
            )
        }
    }
}
