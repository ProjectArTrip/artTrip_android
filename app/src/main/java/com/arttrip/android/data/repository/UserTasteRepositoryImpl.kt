package com.arttrip.android.data.repository

import com.arttrip.android.data.remote.datasource.KeywordDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.keyword.toDomain
import com.arttrip.android.data.remote.model.keyword.UserKeywordsReqDto
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.usertaste.TasteGroup
import com.arttrip.android.domain.repository.UserTasteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserTasteRepositoryImpl
    @Inject
    constructor(
        private val dataSource: KeywordDataSource,
    ) : UserTasteRepository {
        override fun getAllTasteGroups(): Flow<ApiResult<TasteGroup>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val dto =
                        dataSource.getAllKeywords()

                    val groups: TasteGroup = dto.toDomain()

                    emit(ApiResult.Success(groups))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun getUserTasteGroups(): Flow<ApiResult<TasteGroup>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val dto =
                        dataSource.getUserKeywords()

                    val groups: TasteGroup = dto.toDomain()

                    emit(ApiResult.Success(groups))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun saveUserTaste(tasteIds: List<Int>): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    dataSource.postUserKeywords(
                        UserKeywordsReqDto(keywordIds = tasteIds),
                    )
                    emit(ApiResult.Success(Unit))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }
    }
