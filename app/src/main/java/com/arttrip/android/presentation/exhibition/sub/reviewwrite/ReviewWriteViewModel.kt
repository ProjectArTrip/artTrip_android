package com.arttrip.android.presentation.exhibition.sub.reviewwrite

import androidx.lifecycle.ViewModel
import com.arttrip.android.presentation.exhibition.sub.reviewwrite.contract.ReviewWriteEffect
import com.arttrip.android.presentation.exhibition.sub.reviewwrite.contract.ReviewWriteIntent
import com.arttrip.android.presentation.exhibition.sub.reviewwrite.contract.ReviewWriteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ReviewWriteViewModel
    @Inject
    constructor() : ViewModel() {
        private val _state =
            MutableStateFlow(ReviewWriteState())
        val state: StateFlow<ReviewWriteState> = _state

        private val _effect = MutableSharedFlow<ReviewWriteEffect>()
        val effect: SharedFlow<ReviewWriteEffect> = _effect

        private var initialized = false

        fun onIntent(intent: ReviewWriteIntent) {
            when (intent) {
                is ReviewWriteIntent.Initialize -> {
                    if (initialized) return
                    initialized = true
                    _state.update {
                        it.copy(
                            title = intent.prefill.title,
                            hallName = intent.prefill.hallName,
                            posterUrl = intent.prefill.posterUrl,
                        )
                    }
                }

                ReviewWriteIntent.BackClicked -> {}
            }
        }
    }
