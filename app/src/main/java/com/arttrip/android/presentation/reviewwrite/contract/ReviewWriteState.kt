package com.arttrip.android.presentation.reviewwrite.contract

import android.net.Uri
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

const val MAX_REVIEW_PHOTO_COUNT = 4
const val MAX_REVIEW_TEXT_LENGTH = 500
const val MIN_REVIEW_TEXT_LENGTH = 20
private val VISIT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd")

enum class ReviewModeUi {
    CREATE,
    EDIT,
}

data class ReviewWriteState(
    val isInitializing: Boolean = false,
    val isSubmitting: Boolean = false,
    val mode: ReviewModeUi = ReviewModeUi.CREATE,
    val reviewId: Int? = null,
    val exhibitId: Int = -1,
    val appTopBarTitle: String = "리뷰 작성",
    val buttonText: String = "등록하기",
    val title: String = "",
    val hallName: String = "",
    val posterUrl: String? = null,
    val visitDate: LocalDate? = null,
    val isVisitDateSheetVisible: Boolean = false,
    val calendarMonth: YearMonth = YearMonth.now(),
    val reviewText: String = "",
    val photos: List<ReviewPhotoItem> = emptyList(),
    val deletedImageIds: List<Int> = emptyList(),
    val showReviewLengthError: Boolean = false,
    val isProhibitedFishDialogVisible: Boolean = false,
    val isExitConfirmDialogVisible: Boolean = false,
) {
    val canAddPhoto: Boolean get() = photos.size < MAX_REVIEW_PHOTO_COUNT
    val canSubmit: Boolean
        get() =
            !isSubmitting &&
                reviewText.isNotBlank() &&
                visitDate != null

    val visitDateText: String?
        get() = visitDate?.format(VISIT_DATE_FORMATTER)
}

sealed interface ReviewPhotoItem {
    val key: String

    data class Remote(
        val imageId: Int,
        val imageUrl: String,
    ) : ReviewPhotoItem {
        override val key: String = "remote_$imageId"
    }

    data class Local(
        val uri: Uri,
    ) : ReviewPhotoItem {
        override val key: String = "local_$uri"
    }
}
