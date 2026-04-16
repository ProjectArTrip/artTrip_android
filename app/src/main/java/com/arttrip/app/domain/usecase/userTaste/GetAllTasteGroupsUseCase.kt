package com.arttrip.app.domain.usecase.userTaste

import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.model.usertaste.TasteGroup
import com.arttrip.app.domain.repository.UserTasteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTasteGroupsUseCase
    @Inject
    constructor(
        private val userTasteRepository: UserTasteRepository,
    ) {
        operator fun invoke(): Flow<ApiResult<TasteGroup>> = userTasteRepository.getAllTasteGroups()
    }
