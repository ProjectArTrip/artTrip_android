package com.arttrip.app.domain.usecase.profile

import com.arttrip.app.core.model.image.ImageQueryParams
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RefreshProfileUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        operator fun invoke(imageQueryParams: ImageQueryParams): Flow<ApiResult<Unit>> = profileRepository.refreshProfile(imageQueryParams)
    }
