package com.arttrip.app.domain.model.auth

sealed interface SocialLoginCredential {
    data class Kakao(
        val idToken: String,
    ) : SocialLoginCredential

    data class Google(
        val authorizationCode: String,
    ) : SocialLoginCredential
}
