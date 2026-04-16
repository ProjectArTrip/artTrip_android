package com.arttrip.app.domain.usecase.profile

import com.arttrip.app.domain.model.profile.UserProfile
import com.arttrip.app.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveProfileUseCase
    @Inject
    constructor(
        private val repo: ProfileRepository,
    ) {
        operator fun invoke(): StateFlow<UserProfile?> = repo.profileState
    }
