package com.arttrip.app.presentation.mypage.sub.settings.sub.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arttrip.app.domain.model.notice.Notice
import com.arttrip.app.domain.usecase.notice.GetNoticesUseCase
import com.arttrip.app.presentation.mypage.sub.settings.sub.notice.contract.NoticeEffect
import com.arttrip.app.presentation.mypage.sub.settings.sub.notice.contract.NoticeIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel
    @Inject
    constructor(
        private val getNoticesUseCase: GetNoticesUseCase,
    ) : ViewModel() {
        private val _effect = MutableSharedFlow<NoticeEffect>()
        val effect: SharedFlow<NoticeEffect> = _effect

        val noticesFlow: Flow<PagingData<Notice>> =
            getNoticesUseCase().cachedIn(viewModelScope)

        fun onIntent(intent: NoticeIntent) {
            when (intent) {
                NoticeIntent.BackClicked -> viewModelScope.launch { _effect.emit(NoticeEffect.NavigateBack) }
            }
        }
    }
