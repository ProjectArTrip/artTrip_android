package com.arttrip.app.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arttrip.app.core.navigation.app.AppRoute
import com.arttrip.app.core.navigation.main.navigateToExhibitionDetail

@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
    appNavController: NavHostController,
) {
    val mainNavController = rememberNavController()

    val signal = mainViewModel.logoutSignal.collectAsStateWithLifecycle().value
    val pendingExhibitId by mainViewModel.pendingDeepLinkExhibitId.collectAsStateWithLifecycle()

    LaunchedEffect(signal) {
        if (signal > 0) {
            appNavController.navigate(AppRoute.LOGIN) {
                popUpTo(appNavController.graph.id) { inclusive = true }
                launchSingleTop = true
            }

            mainViewModel.consumeLogoutSignal()
        }
    }

    LaunchedEffect(pendingExhibitId) {
        pendingExhibitId?.let { exhibitId ->
            mainNavController.navigateToExhibitionDetail(exhibitId)
            mainViewModel.consumeDeepLink()
        }
    }

    MainScreen(
        modifier = Modifier.fillMaxSize(),
        navController = mainNavController,
    )
}
