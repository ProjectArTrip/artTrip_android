package com.arttrip.app.data.local.fcm

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

        private val _pendingDeepLinkExhibitId = MutableStateFlow<Int?>(null)
        val pendingDeepLinkExhibitId: StateFlow<Int?> = _pendingDeepLinkExhibitId.asStateFlow()

        suspend fun emit(message: FcmMessage) = _messages.emit(message)

        fun emitDeepLink(exhibitId: Int) {
            _pendingDeepLinkExhibitId.value = exhibitId
        }

        fun consumeDeepLink() {
            _pendingDeepLinkExhibitId.value = null
        }
    }
