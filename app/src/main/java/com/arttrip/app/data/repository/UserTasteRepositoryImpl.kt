package com.arttrip.app.data.repository

import com.arttrip.app.data.remote.datasource.KeywordDataSource
import com.arttrip.app.data.remote.mapper.base.toAppError
import com.arttrip.app.data.remote.mapper.keyword.toDomain
import com.arttrip.app.data.remote.mapper.keyword.toRecommendList
import com.arttrip.app.data.remote.model.keyword.UserKeywordsReqDto
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.model.usertaste.Taste
import com.arttrip.app.domain.model.usertaste.TasteGroup
import com.arttrip.app.domain.repository.UserTasteRepository
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
                    emit(ApiResult.Error(t.toAppError()))
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
