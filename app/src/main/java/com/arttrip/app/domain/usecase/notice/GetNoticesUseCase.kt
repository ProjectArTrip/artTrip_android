package com.arttrip.app.domain.usecase.notice

import androidx.paging.PagingData
import com.arttrip.app.domain.model.notice.Notice
import com.arttrip.app.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoticesUseCase
    @Inject
    constructor(
        private val noticeRepository: NoticeRepository,
    ) {
        operator fun invoke(): Flow<PagingData<Notice>> = noticeRepository.getNotices()
    }
