package com.arttrip.android.core.navigation.mypage

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.arttrip.android.core.navigation.main.consumeReviewWriteSuccessResult
import com.arttrip.android.core.navigation.main.navigateToExhibitionDetail
import com.arttrip.android.core.navigation.main.navigateToReviewWrite
import com.arttrip.android.core.navigation.main.navigateToTaste
import com.arttrip.android.presentation.my.MyPageRoute
import com.arttrip.android.presentation.my.sub.editprofile.EditProfileRoute
import com.arttrip.android.presentation.my.sub.myreviews.MyReviewsRoute
import com.arttrip.android.presentation.my.sub.recentexhibitions.RecentExhibitionsRoute
import com.arttrip.android.presentation.my.sub.settings.SettingsRoute
import com.arttrip.android.presentation.my.sub.settings.sub.notice.NoticeRoute
import com.arttrip.android.presentation.my.sub.settings.sub.notification.NotificationRoute

@Composable
fun MyPageNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainNavController: NavHostController,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = MyPageRoute.ROOT,
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
        // MyPage 메인(루트)
        composable(MyPageRoute.ROOT) {
            val actions =
                remember(navController, mainNavController) {
                    MyPageNavActions(
                        toEditProfile = { navController.navigate(MyPageRoute.EDIT_PROFILE) },
                        toRecentExhibitions = { navController.navigate(MyPageRoute.RECENT_EXHIBITIONS) },
                        toMyReviews = { navController.navigate(MyPageRoute.MY_REVIEWS) },
                        toSettings = { navController.navigate(MyPageRoute.SETTINGS) },
                        toTasteAnalysis = { mainNavController.navigateToTaste() },
                    )
                }

            MyPageRoute(
                innerPadding = innerPadding,
                onNavigateEditProfile = actions.toEditProfile,
                onNavigateRecentExhibitions = actions.toRecentExhibitions,
                onNavigateMyReviews = actions.toMyReviews,
                onNavigateTasteAnalysis = actions.toTasteAnalysis,
                onNavigateSettings = actions.toSettings,
            )
        }

        composable(MyPageRoute.EDIT_PROFILE) {
            EditProfileRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
            )
        }
        composable(MyPageRoute.RECENT_EXHIBITIONS) {
            RecentExhibitionsRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
                onNavigateExhibitionDetail = { exhibitId ->
                    mainNavController.navigateToExhibitionDetail(exhibitId)
                },
            )
        }
        composable(MyPageRoute.MY_REVIEWS) {
            var reviewWriteSuccessTick by remember { mutableIntStateOf(0) }
            val currentBackStackEntry by navController.currentBackStackEntryAsState()

            LaunchedEffect(currentBackStackEntry) {
                if (mainNavController.consumeReviewWriteSuccessResult()) {
                    reviewWriteSuccessTick++
                }
            }
            MyReviewsRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
                reviewWriteSuccessTick = reviewWriteSuccessTick,
                onNavigateReviewWrite = { mode ->
                    mainNavController.navigateToReviewWrite(mode)
                },
            )
        }

        composable(MyPageRoute.SETTINGS) {
            SettingsRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
                onNavigateNotice = { navController.navigate(MyPageRoute.NOTICE) },
                onNavigateNotification = { navController.navigate(MyPageRoute.NOTIFICATION) },
            )
        }
        composable(MyPageRoute.NOTIFICATION) {
            NotificationRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
            )
        }
        composable(MyPageRoute.NOTICE) {
            NoticeRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
            )
        }
    }
}
