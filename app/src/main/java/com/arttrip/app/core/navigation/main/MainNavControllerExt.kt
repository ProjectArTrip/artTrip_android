package com.arttrip.app.core.navigation.main

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import com.arttrip.app.core.model.enums.domestic.DomesticRegion
import com.arttrip.app.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.core.navigation.NavKeys
import com.arttrip.app.presentation.reviewwrite.model.ReviewWriteMode
import java.time.LocalDate

/* ================================
 * Public API
 * ================================ */

fun NavHostController.navigateToExhibitionDetail(exhibitId: Int) {
    navigate(MainRoute.exhibitionDetail(exhibitId))
}

fun NavHostController.navigateToNotification() {
    navigate(MainRoute.HOME_NOTIFICATION)
}

fun NavHostController.navigateToDateFilterResult(
    isDomestic: Boolean,
    location: String,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    navigate(MainRoute.dateFilterResult(isDomestic, location, startDate, endDate))
}

fun NavHostController.navigateToSearch() {
    navigate(MainRoute.HOME_SEARCH)
}

fun NavHostController.navigateToRegion(region: DomesticRegion) {
    navigate(MainRoute.region(region))
}

fun NavHostController.navigateToSchedule(
    country: ForeignCountry?,
    date: LocalDate,
) {
    navigate(MainRoute.schedule(country, date))
}

fun NavHostController.navigateToGenre(
    country: ForeignCountry?,
    genre: ExhibitionGenre,
) {
    navigate(MainRoute.genre(country, genre))
}

fun NavHostController.navigateToReviewWrite(mode: ReviewWriteMode) {
    setReviewWriteMode(mode)
    navigate(MainRoute.REVIEW_WRITE)
}

fun NavHostController.navigateToTaste() {
    navigate(MainRoute.TASTE_ANALYSIS)
}

fun NavHostController.navigateToCuration(curationId: Long) {
    navigate(MainRoute.curation(curationId))
}

/**
 * 이전 backStackEntry에서 ReviewWritePrefill을 꺼내오고(consume) 즉시 삭제
 * - ReviewWriteRoute에서 remember로 1회만 읽도록 감싸서 쓰는 걸 권장
 */
fun NavHostController.consumeReviewWriteMode(): ReviewWriteMode? {
    val handle = previousBackStackEntry?.savedStateHandle ?: return null
    return handle.consume<ReviewWriteMode>(NavKeys.REVIEW_WRITE_MODE)
}

fun NavHostController.consumeReviewWriteSuccessResult(): Boolean {
    val handle = currentBackStackEntry?.savedStateHandle ?: return false
    return handle.consume<Boolean>(NavKeys.REVIEW_WRITE_RESULT) ?: false
}

/* ================================
 * Private helpers
 * ================================ */

private fun NavHostController.setReviewWriteMode(mode: ReviewWriteMode) {
    currentBackStackEntry?.savedStateHandle?.set(NavKeys.REVIEW_WRITE_MODE, mode)
}

fun NavHostController.setReviewWriteSuccessResult() {
    previousBackStackEntry
        ?.savedStateHandle
        ?.set(NavKeys.REVIEW_WRITE_RESULT, true)
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
