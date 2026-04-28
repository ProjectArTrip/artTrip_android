package com.arttrip.app.data.remote.mapper.auth

import com.arttrip.app.data.remote.model.auth.LoginResDto
import com.arttrip.app.domain.model.auth.AuthTokens
import com.arttrip.app.domain.model.auth.LoginResult
import com.arttrip.app.domain.model.auth.OnboardingStep

fun LoginResDto.toDomain(): LoginResult =
    LoginResult(
        tokens =
            AuthTokens(
                accessToken = accessToken,
                refreshToken = refreshToken,
            ),
        onboardingStep = onboardingStep.toOnboardingStep(),
    )

private fun String.toOnboardingStep(): OnboardingStep =
    when (this) {
        "NICKNAME" -> OnboardingStep.NICKNAME
        "KEYWORD" -> OnboardingStep.TASTE
        "COMPLETED" -> OnboardingStep.COMPLETED
        else -> OnboardingStep.NICKNAME
    }
