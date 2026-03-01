package com.arttrip.android.domain.usecase.profile

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.profile.UserProfile
import com.arttrip.android.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        operator fun invoke(): Flow<ApiResult<UserProfile>> = profileRepository.getProfile()
    }
