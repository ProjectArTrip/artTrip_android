package com.arttrip.android.presentation.exhibition.sub.reviewwrite

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.presentation.exhibition.sub.reviewwrite.contract.ReviewWriteEffect
import com.arttrip.android.presentation.exhibition.sub.reviewwrite.contract.ReviewWriteIntent
import com.arttrip.android.presentation.exhibition.sub.reviewwrite.contract.ReviewWriteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

                ReviewWriteIntent.BackClicked -> {
                    viewModelScope.launch {
                        _effect.emit(ReviewWriteEffect.NavigateBack)
                    }
                }

                ReviewWriteIntent.VisitDateClicked,
                ReviewWriteIntent.CalendarClicked,
                -> {
                    // TODO: date picker open effect
                }

                ReviewWriteIntent.AddPhotoClicked -> {
                    val s = _state.value
                    if (!s.canAddPhoto) return
                    viewModelScope.launch {
                        _effect.emit(ReviewWriteEffect.LaunchPhotoPicker)
                    }
                }

                is ReviewWriteIntent.PhotoPickerResult -> {
                    _state.update { s ->
                        s.copy(photoUris = mergePhotos(s.photoUris, intent.uris, s.maxPhotoCount))
                    }
                }

                is ReviewWriteIntent.RemovePhotoClicked -> {
                    _state.update { s ->
                        val next =
                            s.photoUris.toMutableList().also { list ->
                                if (intent.index in list.indices) list.removeAt(intent.index)
                            }
                        s.copy(photoUris = next)
                    }
                }

                is ReviewWriteIntent.ReviewTextChanged -> {
                    _state.update { s ->
                        s.copy(reviewText = intent.text.take(s.maxTextLength))
                    }
                }

                ReviewWriteIntent.SubmitClicked -> {
                    submit()
                }
            }
        }

        private fun submit() {
            val snapshot = _state.value
            if (!snapshot.canSubmit) return

            viewModelScope.launch {
                _state.update { it.copy(isSubmitting = true) }
                try {
                    // TODO: 서버 호출
                } finally {
                    _state.update { it.copy(isSubmitting = false) }
                }
            }
        }

        private fun mergePhotos(
            current: List<Uri>,
            incoming: List<Uri>,
            max: Int,
        ): List<Uri> = (current + incoming).take(max)
    }
