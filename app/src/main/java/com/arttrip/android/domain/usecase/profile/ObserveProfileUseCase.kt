package com.arttrip.android.domain.usecase.profile

import com.arttrip.android.domain.model.profile.UserProfile
import com.arttrip.android.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveProfileUseCase
    @Inject
    constructor(
        private val repo: ProfileRepository,
    ) {
        operator fun invoke(): StateFlow<UserProfile?> = repo.profileState
    }
