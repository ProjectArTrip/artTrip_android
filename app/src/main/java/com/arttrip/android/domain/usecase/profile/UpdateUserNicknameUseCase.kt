package com.arttrip.android.domain.usecase.profile

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserNicknameUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        operator fun invoke(nickname: String): Flow<ApiResult<Unit>> = profileRepository.updateUserNickname(nickname)
    }
