package com.arttrip.android.domain.usecase.userTaste

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.usertaste.TasteGroup
import com.arttrip.android.domain.repository.KeywordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasteGroupsUseCase
    @Inject
    constructor(
        private val keywordRepository: KeywordRepository,
    ) {
        operator fun invoke(): Flow<ApiResult<TasteGroup>> = keywordRepository.getAllTasteGroups()
    }
