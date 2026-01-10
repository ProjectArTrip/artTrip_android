package com.arttrip.android.presentation.my.sub.myreviews

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.arttrip.android.presentation.my.sub.myreviews.contract.MyReviewsIntent
import com.arttrip.android.presentation.my.sub.myreviews.contract.MyReviewsState

@Composable
fun MyReviewsScreen(
    innerPadding: PaddingValues,
    state: MyReviewsState,
    onIntent: (MyReviewsIntent) -> Unit,
) {
    Text("MyReviews")
}
