package com.arttrip.android.data.repository

import com.arttrip.android.data.remote.datasource.KeywordDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.keyword.toDomain
import com.arttrip.android.data.remote.mapper.keyword.toRecommendList
import com.arttrip.android.data.remote.model.keyword.UserKeywordsReqDto
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.usertaste.Taste
import com.arttrip.android.domain.model.usertaste.TasteGroup
import com.arttrip.android.domain.repository.UserTasteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

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
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
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
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }

        override fun getRecommendKeywords(): Flow<ApiResult<List<Taste>>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val result = dataSource.getRecommendKeywords().toRecommendList()
                    emit(ApiResult.Success(result))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(ApiError.Unknown(t)))
                }
            }

        override fun saveUserTaste(tastes: List<String>): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    dataSource.postUserKeywords(
                        UserKeywordsReqDto(keywords = tastes),
                    )
                    emit(ApiResult.Success(Unit))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }
    }
