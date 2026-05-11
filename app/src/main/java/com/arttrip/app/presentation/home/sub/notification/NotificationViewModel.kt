package com.arttrip.app.presentation.home.sub.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arttrip.app.core.model.enums.notification.Action
import com.arttrip.app.domain.model.notification.Notification
import com.arttrip.app.domain.repository.NotificationRepository
import com.arttrip.app.domain.usecase.notification.GetNotificationsUseCase
import com.arttrip.app.presentation.home.sub.notification.contract.NotificationEffect
import com.arttrip.app.presentation.home.sub.notification.contract.NotificationIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
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

        private val _localReadIds = MutableStateFlow<Set<Int>>(emptySet())
        val localReadIds: StateFlow<Set<Int>> = _localReadIds

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
                        _localReadIds.update { it + intent.userNoticeId }
                        notificationRepository.readNotification(intent.userNoticeId).collectLatest {}
                        when (intent.action) {
                            Action.MOVE_NOTICE_DETAIL ->
                                _effect.emit(NotificationEffect.NavigateToNotice(intent.referenceId))
                            else -> Unit
                        }
                    }
                }
            }
        }
    }
