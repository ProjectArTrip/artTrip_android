package com.arttrip.android.core.navigation.mypage

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arttrip.android.core.navigation.main.navigateToReviewWrite
import com.arttrip.android.presentation.my.MyPageRoute
import com.arttrip.android.presentation.my.sub.editprofile.EditProfileRoute
import com.arttrip.android.presentation.my.sub.myreviews.MyReviewsRoute

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
                        toTasteAnalysis = { navController.navigate(MyPageRoute.TASTE_ANALYSIS) },
                        toSettings = { navController.navigate(MyPageRoute.SETTINGS) },
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
        }
        composable(MyPageRoute.MY_REVIEWS) {
            MyReviewsRoute(
                innerPadding = innerPadding,
                onBack = navController::popBackStack,
                onNavigateReviewWrite = { exhibitId, prefill ->
                    mainNavController.navigateToReviewWrite(exhibitId, prefill)
                },
            )
        }
        composable(MyPageRoute.TASTE_ANALYSIS) {
        }
        composable(MyPageRoute.SETTINGS) {
        }
    }
}
