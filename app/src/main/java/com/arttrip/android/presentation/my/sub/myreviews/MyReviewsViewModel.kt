package com.arttrip.android.presentation.my.sub.myreviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.presentation.my.sub.myreviews.contract.MyReviewsEffect
import com.arttrip.android.presentation.my.sub.myreviews.contract.MyReviewsIntent
import com.arttrip.android.presentation.my.sub.myreviews.contract.MyReviewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyReviewsViewModel
    @Inject
    constructor() : ViewModel() {
        private val _state = MutableStateFlow(MyReviewsState())
        val state = _state.asStateFlow()

        private val _effect = MutableSharedFlow<MyReviewsEffect>()
        val effect = _effect.asSharedFlow()

        fun onIntent(intent: MyReviewsIntent) {
            when (intent) {
                MyReviewsIntent.BackClicked -> {
                    viewModelScope.launch { _effect.emit(MyReviewsEffect.NavigateBack) }
                }
                MyReviewsIntent.DeleteReviewClicked -> {
                    _state.update { it.copy(isRemoveDialogVisible = true) }
                }
                is MyReviewsIntent.EditReviewClicked -> {
                    val review = intent.review
                    viewModelScope.launch {
                        _effect.emit(
                            MyReviewsEffect.NavigateToReviewEdit(
                                id = review.id,
                                title = review.exhibitionTitle,
                                hallName = "예시 hallName",
                                posterUrl = review.thumbnailUrl,
                                reviewText = review.content,
                            ),
                        )
                    }
                }
                MyReviewsIntent.RemoveConfirmClicked -> {
                    _state.update { it.copy(isRemoveDialogVisible = false) }

                    // TODO 리뷰삭제API
                }
                MyReviewsIntent.RemoveDialogDismissed -> _state.update { it.copy(isRemoveDialogVisible = false) }
            }
        }
    }
