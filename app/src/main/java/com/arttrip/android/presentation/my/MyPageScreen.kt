package com.arttrip.android.presentation.my

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
) {
    val viewModel: MyPageViewModel = hiltViewModel()
    Column(
        modifier = modifier.padding(innerPadding),
    ) {
        Text(
            text = "Mypage",
        )
        Button(
            onClick = { viewModel.logout() },
        ) {
            Text(text = "임시 로그아웃")
        }
    }
}
