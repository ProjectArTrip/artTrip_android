package com.arttrip.app.presentation.intro.nickname

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.app.core.navigation.app.AppRoute
import com.arttrip.app.core.util.LocalToastController
import com.arttrip.app.presentation.intro.nickname.contract.NicknameEffect

@Composable
fun NicknameRoute(
    innerPadding: PaddingValues,
    viewModel: NicknameViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = LocalToastController.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                NicknameEffect.NavigateToTaste ->
                    onNavigate(AppRoute.INTRO_TASTE)

                is NicknameEffect.ShowToast -> {
                    toast.show(effect.message)
                }
            }
        }
    }

    NicknameScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
