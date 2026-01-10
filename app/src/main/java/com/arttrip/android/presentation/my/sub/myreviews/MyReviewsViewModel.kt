package com.arttrip.android.presentation.my.sub.myreviews

import androidx.lifecycle.ViewModel
import com.arttrip.android.presentation.my.sub.myreviews.contract.MyReviewsEffect
import com.arttrip.android.presentation.my.sub.myreviews.contract.MyReviewsIntent
import com.arttrip.android.presentation.my.sub.myreviews.contract.MyReviewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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
        }
    }
