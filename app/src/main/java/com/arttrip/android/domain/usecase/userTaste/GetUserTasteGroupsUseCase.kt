package com.arttrip.android.domain.usecase.userTaste

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.usertaste.TasteGroup
import com.arttrip.android.domain.repository.UserTasteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserTasteGroupsUseCase
    @Inject
    constructor(
        private val userTasteRepository: UserTasteRepository,
    ) {
        operator fun invoke(): Flow<ApiResult<TasteGroup>> = userTasteRepository.getUserTasteGroups()
    }
