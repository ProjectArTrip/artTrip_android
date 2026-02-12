package com.arttrip.android.presentation.main

import ToastController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arttrip.android.core.navigation.app.AppRoute
import com.arttrip.android.core.ui.component.toast.AppToastHost
import com.arttrip.android.core.util.LocalToastController

@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
    appNavController: NavHostController,
) {
    val mainNavController = rememberNavController()
    val scope = rememberCoroutineScope()
    val toastController = remember { ToastController(scope) }

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

    CompositionLocalProvider(
        LocalToastController provides toastController,
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
        ) {
            MainScreen(
                modifier = Modifier.fillMaxSize(),
                navController = mainNavController,
            )

            AppToastHost(
                hostState = toastController.hostState,
            )
        }
    }
}
