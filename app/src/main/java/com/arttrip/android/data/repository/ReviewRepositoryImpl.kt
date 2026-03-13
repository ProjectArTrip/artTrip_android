package com.arttrip.android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arttrip.android.core.util.compressImageForUpload
import com.arttrip.android.core.util.toMultipartPart
import com.arttrip.android.data.remote.datasource.ReviewDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.review.toDomain
import com.arttrip.android.data.remote.mapper.review.toRequestBody
import com.arttrip.android.data.remote.model.review.CreateReviewReqDto
import com.arttrip.android.data.remote.model.review.UpdateReviewReqDto
import com.arttrip.android.data.remote.paging.review.ExhibitReviewPagingSource
import com.arttrip.android.data.remote.paging.review.UserReviewPagingSource
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.review.ExhibitionReview
import com.arttrip.android.domain.model.review.ReviewDetail
import com.arttrip.android.domain.model.review.UserReview
import com.arttrip.android.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class ReviewRepositoryImpl
    @Inject
    constructor(
        private val reviewDataSource: ReviewDataSource,
    ) : ReviewRepository {
        private val _exhibitReviewTotalCount = MutableStateFlow<Int?>(null)
        override val exhibitReviewTotalCount: StateFlow<Int?> = _exhibitReviewTotalCount.asStateFlow()

        private val _userReviewTotalCount = MutableStateFlow<Int?>(null)
        override val userReviewTotalCount: StateFlow<Int?> = _userReviewTotalCount.asStateFlow()

        override fun clearExhibitReviewTotalCount() {
            _exhibitReviewTotalCount.value = null
        }

        override fun clearUserReviewTotalCount() {
            _userReviewTotalCount.value = null
        }

        override fun getExhibitionReviews(
            exhibitId: Int,
            pageSize: Int,
            initialLoadSize: Int,
        ): Flow<PagingData<ExhibitionReview>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = pageSize,
                        initialLoadSize = initialLoadSize,
                        prefetchDistance = 1,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = {
                    ExhibitReviewPagingSource(
                        dataSource = reviewDataSource,
                        exhibitId = exhibitId,
                        onTotalCount = { count ->
                            _exhibitReviewTotalCount.value = count
                        },
                    )
                },
            ).flow

        override fun getReview(reviewId: Int): Flow<ApiResult<ReviewDetail>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val dto =
                        reviewDataSource.getReviewDetail(
                            reviewId = reviewId,
                        )
                    emit(ApiResult.Success(dto.toDomain()))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }

        override fun createReview(
            exhibitId: Int,
            date: String,
            content: String,
            files: List<File>,
        ): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val requestBody =
                        CreateReviewReqDto(
                            date = date,
                            content = content,
                        ).toRequestBody()

                    val parts =
                        files
                            .takeIf { it.isNotEmpty() }
                            ?.map { file ->
                                file
                                    .compressImageForUpload(targetMaxBytes = 1_500_000L)
                                    .toMultipartPart(fieldName = "images")
                            }

                    reviewDataSource.postReview(
                        exhibitId = exhibitId,
                        request = requestBody,
                        parts = parts,
                    )
                    emit(ApiResult.Success(Unit))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }

        override fun deleteReview(reviewId: Int): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    reviewDataSource.deleteReview(reviewId)
                    emit(ApiResult.Success(Unit))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }

        override fun updateReview(
            reviewId: Int,
            date: String,
            content: String,
            deletedImageIds: List<Int>,
            files: List<File>,
        ): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val requestBody =
                        UpdateReviewReqDto(
                            date = date,
                            content = content,
                            deleteImageIds = deletedImageIds.takeIf { it.isNotEmpty() },
                        ).toRequestBody()

                    val parts =
                        files
                            .takeIf { it.isNotEmpty() }
                            ?.map { file ->
                                file
                                    .compressImageForUpload(targetMaxBytes = 1_500_000L)
                                    .toMultipartPart(fieldName = "images")
                            }

                    reviewDataSource.patchReview(
                        reviewId = reviewId,
                        request = requestBody,
                        parts = parts,
                    )
                    emit(ApiResult.Success(Unit))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }

        override fun getUserReviews(
            pageSize: Int,
            initialLoadSize: Int,
        ): Flow<PagingData<UserReview>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = pageSize,
                        initialLoadSize = initialLoadSize,
                        prefetchDistance = 1,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = {
                    UserReviewPagingSource(
                        dataSource = reviewDataSource,
                        onTotalCount = { count -> _userReviewTotalCount.value = count },
                    )
                },
            ).flow
    }
