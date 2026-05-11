package com.arttrip.app.data.local.fcm

import com.arttrip.app.core.model.enums.notification.Action
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmEventBus
    @Inject
    constructor() {
        private val _messages = MutableSharedFlow<FcmMessage>()
        val messages: SharedFlow<FcmMessage> = _messages.asSharedFlow()

        private val _pendingDeepLink = MutableStateFlow<Pair<Action, Int?>?>(null)
        val pendingDeepLink: StateFlow<Pair<Action, Int?>?> = _pendingDeepLink.asStateFlow()

        suspend fun emit(message: FcmMessage) = _messages.emit(message)

        fun emitDeepLink(
            action: Action,
            referenceId: Int?,
        ) {
            _pendingDeepLink.value = action to referenceId
        }

        fun consumeDeepLink() {
            _pendingDeepLink.value = null
        }
    }
