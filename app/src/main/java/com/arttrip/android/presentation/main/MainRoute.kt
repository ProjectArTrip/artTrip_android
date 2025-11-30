package com.arttrip.android.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.arttrip.android.core.navigation.BottomNavItem
import com.arttrip.android.presentation.main.contract.MainIntent

@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val uiState by mainViewModel.state.collectAsStateWithLifecycle()

    val onLoginSuccess: (Boolean) -> Unit = { isFirstLogin ->
        mainViewModel.dispatch(MainIntent.OnLoginSuccess)
        val route =
            if (isFirstLogin) "intro" else BottomNavItem.Home.route

        navController.navigate(route) {
            popUpTo("login") { inclusive = true }
        }
    }

    MainScreen(
        modifier = modifier,
        navController = navController,
        uiState = uiState,
        onLoginSuccess = onLoginSuccess,
    )
}
