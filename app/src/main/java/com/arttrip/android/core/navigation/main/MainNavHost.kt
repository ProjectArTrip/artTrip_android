package com.arttrip.android.core.navigation.main

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.core.navigation.mypage.MyPageNavHost
import com.arttrip.android.presentation.bookmark.BookmarkRoute
import com.arttrip.android.presentation.exhibition.ExhibitionDetailRoute
import com.arttrip.android.presentation.home.HomeRoute
import com.arttrip.android.presentation.home.sub.dateresult.DateResultRoute
import com.arttrip.android.presentation.home.sub.genre.GenreRoute
import com.arttrip.android.presentation.home.sub.notification.NotificationRoute
import com.arttrip.android.presentation.home.sub.region.RegionRoute
import com.arttrip.android.presentation.home.sub.schedule.ScheduleRoute
import com.arttrip.android.presentation.home.sub.search.SearchRoute
import com.arttrip.android.presentation.map.MapRoute
import com.arttrip.android.presentation.mypage.sub.taste.TasteRoute
import com.arttrip.android.presentation.reviewwrite.ReviewWriteRoute
import com.arttrip.android.presentation.stamp.StampRoute
import java.time.LocalDate

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
                },
                onNavigateSchedule = { country, date ->
                    navController.navigateToSchedule(country, date)
                },
                onNavigateGenre = { country, genre ->
                    navController.navigateToGenre(country, genre)
                },
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
                onBack = navController::popBackStack,
            )
        }

        composable(MainRoute.HOME_SEARCH) {
            SearchRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
                onNavigateExhibitionDetail = { id ->
                    navController.navigateToExhibitionDetail(id)
                },
            )
        }

        composable(
            route = MainRoute.HOME_REGION,
            arguments =
                listOf(
                    navArgument("region") { type = NavType.StringType },
                ),
        ) { backStackEntry ->
            val regionName = backStackEntry.arguments?.getString("region") ?: return@composable

            val region =
                runCatching { DomesticRegion.valueOf(regionName) }
                    .getOrNull() ?: return@composable

            RegionRoute(
                innerPadding = innerPadding,
                region = region,
                onBack = navController::popBackStack,
                onNavigateExhibitionDetail = { id ->
                    navController.navigateToExhibitionDetail(id)
                },
            )
        }

        composable(
            route = MainRoute.HOME_SCHEDULE,
            arguments =
                listOf(
                    navArgument("country") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    },
                    navArgument("date") {
                        type = NavType.StringType
                    },
                ),
        ) { backStackEntry ->
            val countryName = backStackEntry.arguments?.getString("country")
            val country = countryName?.let { ForeignCountry.valueOf(it) }

            val dateString = backStackEntry.arguments?.getString("date")
            val date = dateString?.let { LocalDate.parse(it) }!!

            ScheduleRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
                onNavigateNotification = navController::navigateToNotification,
                country = country,
                date = date,
            )
        }

        composable(
            route = MainRoute.HOME_GENRE,
            arguments =
                listOf(
                    navArgument("country") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    },
                    navArgument("genre") {
                        type = NavType.StringType
                    },
                ),
        ) { backStackEntry ->
            val countryName = backStackEntry.arguments?.getString("country")
            val country = countryName?.let { ForeignCountry.valueOf(it) }

            val genre =
                ExhibitionGenre.valueOf(
                    backStackEntry.arguments?.getString("genre")!!,
                )

            GenreRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
                onNavigateNotification = navController::navigateToNotification,
                onNavigateExhibitionDetail = { id -> navController.navigateToExhibitionDetail(id) },
                country = country,
                genre = genre,
            )
        }

        composable(MainRoute.EXHIBITION_DETAIL, listOf(navArgument("exhibitId") { type = NavType.IntType })) { backStackEntry ->
            val exhibitId = backStackEntry.arguments?.getInt("exhibitId") ?: return@composable
            var reviewWriteSuccessTick by remember { mutableIntStateOf(0) }
            val currentBackStackEntry by navController.currentBackStackEntryAsState()

            LaunchedEffect(currentBackStackEntry) {
                if (navController.consumeReviewWriteSuccessResult()) {
                    reviewWriteSuccessTick++
                }
            }

            ExhibitionDetailRoute(
                innerPadding,
                exhibitId,
                onBack = navController::popBackStack,
                onNavigateReviewWrite = { mode ->
                    navController.navigateToReviewWrite(mode)
                },
                reviewWriteSuccessTick = reviewWriteSuccessTick,
            )
        }

        composable(
            route = MainRoute.REVIEW_WRITE,
        ) { _ ->
            val mode = remember { navController.consumeReviewWriteMode() } ?: return@composable

            ReviewWriteRoute(
                innerPadding = innerPadding,
                mode = mode,
                onBack = navController::popBackStack,
                onSuccessBack = {
                    navController.setReviewWriteSuccessResult()
                    navController.popBackStack()
                },
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
