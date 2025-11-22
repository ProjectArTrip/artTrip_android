package com.arttrip.android.domain.usecase.user

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.user.UserInfoModel
import com.arttrip.android.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCase
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) {
        operator fun invoke(id: Int): Flow<ApiResult<UserInfoModel>> = userRepository.getUserInfo(id)
    }
