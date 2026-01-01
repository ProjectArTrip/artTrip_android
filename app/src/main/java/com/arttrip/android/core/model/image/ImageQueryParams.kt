package com.arttrip.android.core.model.image

/**
 * 모든 이미지 관련 API에 공통으로 붙는 쿼리 파라미터 묶음.
 *
 * 서버 요구사항: w(폭), h(높이), f(포맷)
 * - 단위는 "px" 기준으로 통일
 *
 * 주의:
 * - widthPx/heightPx는 1 이상
 * - format은 기본값 WEBP
 */
data class ImageQueryParams(
    val widthPx: Int,
    val heightPx: Int,
    val format: ImageFormat = ImageFormat.WEBP,
) {
    init {
        require(widthPx > 0) { "widthPx must be > 0 (was $widthPx)" }
        require(heightPx > 0) { "heightPx must be > 0 (was $heightPx)" }
    }
}
