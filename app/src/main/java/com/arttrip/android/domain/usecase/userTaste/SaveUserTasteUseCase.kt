package com.arttrip.android.domain.usecase.userTaste

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.UserTasteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveUserTasteUseCase
    @Inject
    constructor(
        private val userTasteRepository: UserTasteRepository,
    ) {
        operator fun invoke(
            genres: Set<String>,
            styles: Set<String>,
        ): Flow<ApiResult<Unit>> =
            userTasteRepository.saveUserTaste(
                (genres + styles).toList(),
            )
    }
