package com.arttrip.android.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.arttrip.android.core.navigation.AppRoute

@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()

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
    )
}
