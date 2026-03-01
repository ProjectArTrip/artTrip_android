package com.arttrip.android.domain.usecase.profile

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class UpdateProfileImageUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        operator fun invoke(file: File): Flow<ApiResult<Unit>> = profileRepository.updateProfileImage(file)
    }
