package com.arttrip.app.domain.usecase.profile

import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteUserAccountUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) {
        operator fun invoke(): Flow<ApiResult<Unit>> = authRepository.deleteUserAccount()
    }
