package com.arttrip.android.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.arttrip.android.core.navigation.AppRoute
import com.arttrip.android.presentation.main.contract.MainIntent

@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val uiState by mainViewModel.state.collectAsStateWithLifecycle()

    val onLoginSuccess: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(AppRoute.LOGIN) { inclusive = true }
        }
        mainViewModel.dispatch(MainIntent.OnLoginSuccess)
    }

    LaunchedEffect(Unit) {
        mainViewModel.logoutEvents.collect {
            navController.navigate(AppRoute.LOGIN) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    MainScreen(
        modifier = modifier,
        navController = navController,
        uiState = uiState,
        onLoginSuccess = onLoginSuccess,
    )
}
