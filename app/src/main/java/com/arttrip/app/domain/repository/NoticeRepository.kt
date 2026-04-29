package com.arttrip.app.domain.repository

import androidx.paging.PagingData
import com.arttrip.app.domain.model.notice.Notice
import kotlinx.coroutines.flow.Flow

interface NoticeRepository {
    fun getNotices(
        pageSize: Int = 10,
        initialLoadSize: Int = 10,
    ): Flow<PagingData<Notice>>
}
