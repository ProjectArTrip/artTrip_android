package com.arttrip.android.core.navigation.main

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import com.arttrip.android.core.navigation.NavKeys
import com.arttrip.android.core.navigation.main.MainRoute
import com.arttrip.android.presentation.reviewwrite.model.ReviewWritePrefill

/* ================================
 * Public API
 * ================================ */

fun NavHostController.navigateToExhibitionDetail(exhibitId: Int) {
    navigate(MainRoute.exhibitionDetail(exhibitId))
}

fun NavHostController.navigateToReviewWrite(
    exhibitId: Int,
    prefill: ReviewWritePrefill,
) {
    setReviewWritePrefill(prefill)
    navigate(MainRoute.reviewWrite(exhibitId))
}

/**
 * 이전 backStackEntry에서 ReviewWritePrefill을 꺼내오고(consume) 즉시 삭제
 * - ReviewWriteRoute에서 remember로 1회만 읽도록 감싸서 쓰는 걸 권장
 */
fun NavHostController.consumeReviewWritePrefill(): ReviewWritePrefill? {
    val handle = previousBackStackEntry?.savedStateHandle ?: return null
    return handle.consume<ReviewWritePrefill>(NavKeys.REVIEW_WRITE_PREFILL)
}

/* ================================
 * Private helpers
 * ================================ */

private fun NavHostController.setReviewWritePrefill(prefill: ReviewWritePrefill) {
    currentBackStackEntry?.savedStateHandle?.set(NavKeys.REVIEW_WRITE_PREFILL, prefill)
}

/**
 * SavedStateHandle 값 가져오고 즉시 삭제하는 helper
 * - key 기반으로 재사용 가능
 */
private inline fun <reified T> SavedStateHandle.consume(key: String): T? {
    val value = get<T>(key)
    remove<T>(key)
    return value
}
