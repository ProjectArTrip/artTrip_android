package com.arttrip.android.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arttrip.android.core.navigation.app.AppRoute

@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
    appNavController: NavHostController,
) {
    val mainNavController = rememberNavController()

    val signal = mainViewModel.logoutSignal.collectAsStateWithLifecycle().value

    LaunchedEffect(signal) {
        if (signal > 0) {
            appNavController.navigate(AppRoute.LOGIN) {
                popUpTo(appNavController.graph.id) { inclusive = true }
                launchSingleTop = true
            }

            mainViewModel.consumeLogoutSignal()
        }
    }

    MainScreen(
        modifier = modifier,
        navController = mainNavController,
    )
}
