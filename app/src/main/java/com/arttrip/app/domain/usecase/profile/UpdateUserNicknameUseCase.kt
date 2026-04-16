package com.arttrip.app.domain.usecase.profile

import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserNicknameUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        operator fun invoke(nickname: String): Flow<ApiResult<Unit>> = profileRepository.updateUserNickname(nickname)
    }
