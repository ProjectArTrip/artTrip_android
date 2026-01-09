package com.arttrip.android.core.navigation.mypage

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arttrip.android.core.navigation.main.navigateToReviewWrite
import com.arttrip.android.presentation.my.MyPageRoute
import com.arttrip.android.presentation.my.sub.editprofile.EditProfileRoute

@Composable
fun MyPageNavHost(
    navController: NavHostController,
    mainNavController: NavHostController,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = MyPageRoute.ROOT,
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
                        toReviewWrite = { exhibitId, prefill ->
                            mainNavController.navigateToReviewWrite(exhibitId, prefill)
                        },
                    )
                }

            MyPageRoute(
                innerPadding = innerPadding,
                onNavigateEditProfile = actions.toEditProfile,
                onNavigateRecentExhibitions = actions.toRecentExhibitions,
                onNavigateMyReviews = actions.toMyReviews,
                onNavigateReviewWrite = actions.toReviewWrite,
                onNavigateTasteAnalysis = actions.toTasteAnalysis,
                onNavigateSettings = actions.toSettings,
            )
        }

        composable(MyPageRoute.EDIT_PROFILE) {
        }
        composable(MyPageRoute.RECENT_EXHIBITIONS) {
        }
        composable(MyPageRoute.MY_REVIEWS) {
        }
        composable(MyPageRoute.TASTE_ANALYSIS) {
        }
        composable(MyPageRoute.SETTINGS) {
        }
    }
}
