package com.arttrip.app.domain.model.auth

enum class LoginProvider(
    val value: String,
) {
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE"),
}
