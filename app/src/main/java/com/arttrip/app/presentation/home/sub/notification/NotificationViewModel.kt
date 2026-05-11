package com.arttrip.app.presentation.home.sub.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.model.notification.Notification
import com.arttrip.app.domain.repository.NotificationRepository
import com.arttrip.app.domain.usecase.notification.GetNotificationsUseCase
import com.arttrip.app.presentation.home.sub.notification.contract.NotificationEffect
import com.arttrip.app.presentation.home.sub.notification.contract.NotificationIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel
    @Inject
    constructor(
        private val getNotificationsUseCase: GetNotificationsUseCase,
        private val notificationRepository: NotificationRepository,
    ) : ViewModel() {
        private val _effect = MutableSharedFlow<NotificationEffect>()
        val effect: SharedFlow<NotificationEffect> = _effect

        val notificationsFlow: Flow<PagingData<Notification>> =
            getNotificationsUseCase().cachedIn(viewModelScope)

        fun onIntent(intent: NotificationIntent) {
            when (intent) {
                NotificationIntent.BackClicked -> {
                    viewModelScope.launch {
                        _effect.emit(NotificationEffect.NavigateBack)
                    }
                }
                is NotificationIntent.NotificationItemClicked -> {
                    viewModelScope.launch {
                        notificationRepository.readNotification(intent.userNoticeId).collectLatest {}
                    }
                }
            }
        }
    }
