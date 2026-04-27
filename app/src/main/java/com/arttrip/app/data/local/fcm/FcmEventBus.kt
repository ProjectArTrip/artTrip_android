package com.arttrip.app.data.local.fcm

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmEventBus
    @Inject
    constructor() {
        private val _messages = MutableSharedFlow<FcmMessage>()
        val messages: SharedFlow<FcmMessage> = _messages.asSharedFlow()

        suspend fun emit(message: FcmMessage) = _messages.emit(message)
    }
