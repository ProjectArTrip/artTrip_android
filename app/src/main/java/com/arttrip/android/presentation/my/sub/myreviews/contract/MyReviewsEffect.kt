package com.arttrip.android.presentation.my.sub.myreviews.contract

sealed interface MyReviewsEffect {
    data object NavigateBack : MyReviewsEffect

    data class NavigateToReviewEdit(
        val id: Int,
        val title: String,
        val hallName: String,
        val posterUrl: String?,
        val reviewText: String,
    ) : MyReviewsEffect
}
