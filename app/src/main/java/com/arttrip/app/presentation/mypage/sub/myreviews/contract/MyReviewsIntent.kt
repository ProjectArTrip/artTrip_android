package com.arttrip.app.presentation.mypage.sub.myreviews.contract

import com.arttrip.app.domain.model.review.UserReview

sealed interface MyReviewsIntent {
    data object BackClicked : MyReviewsIntent

    data object OnReviewEditSuccess : MyReviewsIntent

    data class DeleteReviewClicked(
        val reviewId: Int,
    ) : MyReviewsIntent

    data class EditReviewClicked(
        val review: UserReview,
    ) : MyReviewsIntent

    data object RemoveDialogDismissed : MyReviewsIntent

    data object RemoveConfirmClicked : MyReviewsIntent
}
