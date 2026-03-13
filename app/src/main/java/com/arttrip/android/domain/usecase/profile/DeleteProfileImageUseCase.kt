package com.arttrip.android.domain.usecase.profile

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteProfileImageUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        operator fun invoke(): Flow<ApiResult<Unit>> = profileRepository.deleteProfileImage()
    }
