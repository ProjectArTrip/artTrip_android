package com.arttrip.app.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arttrip.app.data.remote.datasource.UserNoticeDataSource
import com.arttrip.app.data.remote.paging.usernotice.NoticePagingSource
import com.arttrip.app.domain.model.notice.Notice
import com.arttrip.app.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoticeRepositoryImpl
    @Inject
    constructor(
        private val noticeDataSource: UserNoticeDataSource,
    ) : NoticeRepository {
        override fun getNotices(
            pageSize: Int,
            initialLoadSize: Int,
        ): Flow<PagingData<Notice>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = pageSize,
                        initialLoadSize = initialLoadSize,
                        prefetchDistance = 1,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = {
                    NoticePagingSource(
                        dataSource = noticeDataSource,
                    )
                },
            ).flow
    }
