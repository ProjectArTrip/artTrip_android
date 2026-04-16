package com.arttrip.app.domain.usecase.profile

import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.ProfileRepository
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
