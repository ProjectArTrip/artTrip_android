package com.arttrip.android.presentation.my

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.presentation.my.contract.MyPageState

@Composable
fun MyPageScreen(
    innerPadding: PaddingValues,
    state: MyPageState,
    onNavigateExhibitionDetail: (Int) -> Unit,
) {
    val viewModel: MyPageViewModel = hiltViewModel()

    val isBookmarked by viewModel.bookmarkStore
        .bookmarkedFlow(state.exhibitId)
        .collectAsStateWithLifecycle(false)
    Column(
        modifier = Modifier.padding(innerPadding),
    ) {
        Text(
            text = "Mypage",
        )
        Button(
            onClick = { viewModel.logout() },
        ) {
            Text(text = "임시 로그아웃")
        }
        Button(
            onClick = { onNavigateExhibitionDetail(state.exhibitId) },
        ) {
            Text(text = "임시 전시조회화면 ${state.exhibitId} : $isBookmarked")
        }
    }
}
