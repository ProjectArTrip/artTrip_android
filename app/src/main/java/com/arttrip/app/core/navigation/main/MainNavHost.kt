package com.arttrip.app.core.navigation.main

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
import androidx.navigation.navArgument
import com.arttrip.app.core.model.enums.domestic.DomesticRegion
import com.arttrip.app.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.presentation.bookmark.BookmarkRoute
import com.arttrip.app.presentation.exhibition.ExhibitionDetailRoute
import com.arttrip.app.presentation.home.HomeRoute
import com.arttrip.app.presentation.home.sub.curation.CurationRoute
import com.arttrip.app.presentation.home.sub.datefilterresult.DateFilterResultRoute
import com.arttrip.app.presentation.home.sub.genre.GenreRoute
import com.arttrip.app.presentation.home.sub.notification.NotificationRoute
import com.arttrip.app.presentation.home.sub.region.RegionRoute
import com.arttrip.app.presentation.home.sub.schedule.ScheduleRoute
import com.arttrip.app.presentation.home.sub.search.SearchRoute
import com.arttrip.app.presentation.map.MapRoute
import com.arttrip.app.presentation.mypage.MyPageRoute
import com.arttrip.app.presentation.mypage.sub.editprofile.EditProfileRoute
import com.arttrip.app.presentation.mypage.sub.myreviews.MyReviewsRoute
import com.arttrip.app.presentation.mypage.sub.recentexhibitions.RecentExhibitionsRoute
import com.arttrip.app.presentation.mypage.sub.settings.SettingsRoute
import com.arttrip.app.presentation.mypage.sub.taste.TasteRoute
import com.arttrip.app.presentation.reviewwrite.ReviewWriteRoute
import com.arttrip.app.presentation.stamp.StampRoute
import java.time.LocalDate
import com.arttrip.app.presentation.mypage.sub.settings.sub.notice.NoticeRoute as MyPageNoticeRoute
import com.arttrip.app.presentation.mypage.sub.settings.sub.notification.NotificationRoute as MyPageNotificationRoute

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
                onNavigateDateFilterResult = { isDomestic, location, startDate, endDate ->
                    navController.navigateToDateFilterResult(isDomestic, location, startDate, endDate)
                },
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
                onNavigateCuration = { curationId ->
                    navController.navigateToCuration(curationId)
                },
            )
        }
        composable(BottomNavItem.Map.route) {
            MapRoute(
                innerPadding = innerPadding,
                onNavigateExhibitionDetail = { exhibitId ->
                    navController.navigateToExhibitionDetail(exhibitId)
                },
            )
        }
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
            MyPageRoute(
                innerPadding = innerPadding,
                onNavigateEditProfile = { navController.navigate(MainRoute.MY_PAGE_EDIT_PROFILE) },
                onNavigateRecentExhibitions = { navController.navigate(MainRoute.MY_PAGE_RECENT_EXHIBITIONS) },
                onNavigateMyReviews = { navController.navigate(MainRoute.MY_PAGE_MY_REVIEWS) },
                onNavigateTasteAnalysis = { navController.navigateToTaste() },
                onNavigateSettings = { navController.navigate(MainRoute.MY_PAGE_SETTINGS) },
            )
        }

        // MyPage 하위 화면
        composable(MainRoute.MY_PAGE_EDIT_PROFILE) {
            EditProfileRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
            )
        }
        composable(MainRoute.MY_PAGE_RECENT_EXHIBITIONS) {
            RecentExhibitionsRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
                onNavigateExhibitionDetail = { exhibitId ->
                    navController.navigateToExhibitionDetail(exhibitId)
                },
            )
        }
        composable(MainRoute.MY_PAGE_MY_REVIEWS) {
            var reviewWriteSuccessTick by remember { mutableIntStateOf(0) }
            val currentBackStackEntry by navController.currentBackStackEntryAsState()

            LaunchedEffect(currentBackStackEntry) {
                if (navController.consumeReviewWriteSuccessResult()) {
                    reviewWriteSuccessTick++
                }
            }
            MyReviewsRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
                reviewWriteSuccessTick = reviewWriteSuccessTick,
                onNavigateReviewWrite = { mode ->
                    navController.navigateToReviewWrite(mode)
                },
            )
        }
        composable(MainRoute.MY_PAGE_SETTINGS) {
            SettingsRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
                onNavigateNotice = { navController.navigate(MainRoute.MY_PAGE_NOTICE) },
                onNavigateNotification = { navController.navigate(MainRoute.MY_PAGE_NOTIFICATION) },
            )
        }
        composable(MainRoute.MY_PAGE_NOTIFICATION) {
            MyPageNotificationRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
            )
        }
        composable(MainRoute.MY_PAGE_NOTICE) {
            MyPageNoticeRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
            )
        }

        composable(
            route = MainRoute.HOME_DATE_FILTER_RESULT,
            arguments =
                listOf(
                    navArgument("isDomestic") { type = NavType.BoolType },
                    navArgument("location") { type = NavType.StringType },
                    navArgument("startDate") { type = NavType.StringType },
                    navArgument("endDate") { type = NavType.StringType },
                ),
        ) { backStackEntry ->
            val isDomestic = backStackEntry.arguments?.getBoolean("isDomestic")!!
            val location = backStackEntry.arguments?.getString("location")!!
            val startDate = LocalDate.parse(backStackEntry.arguments?.getString("startDate")!!)
            val endDate = LocalDate.parse(backStackEntry.arguments?.getString("endDate")!!)
            DateFilterResultRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
                isDomestic = isDomestic,
                location = location,
                startDate = startDate,
                endDate = endDate,
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
                onNavigateToExhibitionDetail = navController::navigateToExhibitionDetail,
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

        composable(
            route = MainRoute.HOME_CURATION,
            arguments =
                listOf(
                    navArgument("curationId") { type = NavType.LongType },
                ),
        ) { backStackEntry ->
            val curationId = backStackEntry.arguments?.getLong("curationId") ?: return@composable

            CurationRoute(
                innerPadding = innerPadding,
                curationId = curationId,
                onBack = navController::popBackStack,
                onNavigateNotification = navController::navigateToNotification,
                onNavigateExhibitionDetail = { id -> navController.navigateToExhibitionDetail(id) },
            )
        }
    }
}
