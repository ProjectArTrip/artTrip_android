package com.arttrip.android.core.model.image

/**
 * 서버에 넘길 이미지 포맷.
 *
 * - value: 서버 쿼리 파라미터(f=)로 들어갈 문자열 값
 */
enum class ImageFormat(
    val value: String,
) {
    WEBP("webp"),
    JPG("jpg"),
    PNG("png"),
}
