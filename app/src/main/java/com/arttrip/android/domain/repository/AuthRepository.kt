package com.arttrip.android.domain.repository

import com.arttrip.android.domain.model.auth.LoginResult


interface AuthRepository {
    suspend fun loginWithKakao(idToken: String): LoginResult
}