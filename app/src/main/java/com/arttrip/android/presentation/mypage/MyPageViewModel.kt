package com.arttrip.android.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.data.local.auth.SessionManager
import com.arttrip.android.domain.usecase.profile.ObserveProfileUseCase
import com.arttrip.android.presentation.mypage.contract.MyPageEffect
import com.arttrip.android.presentation.mypage.contract.MyPageIntent
import com.arttrip.android.presentation.mypage.contract.MyPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel
    @Inject
    constructor(
        private val observeProfile: ObserveProfileUseCase,
        private val sessionManager: SessionManager,
    ) : ViewModel() {
        private val _state = MutableStateFlow(MyPageState())
        val state: StateFlow<MyPageState> = _state

        private val _effect = MutableSharedFlow<MyPageEffect>()
        val effect: SharedFlow<MyPageEffect> = _effect

        init {

            viewModelScope.launch {
                observeProfile().collect { profile ->
                    if (profile != null) {
                        _state.update {
                            it.copy(
                                userName = profile.nickname,
                                profileImageUrl = profile.profileImageUrl,
                            )
                        }
                    }
                }
            }
        }

        fun onIntent(intent: MyPageIntent) {
            when (intent) {
                MyPageIntent.ClickProfileMore -> {
                    viewModelScope.launch { _effect.emit(MyPageEffect.NavigateToEditProfile) }
                }

                MyPageIntent.ClickRecentExhibitions -> {
                    viewModelScope.launch { _effect.emit(MyPageEffect.NavigateToRecentExhibitions) }
                }

                MyPageIntent.ClickMyReviews -> {
                    viewModelScope.launch { _effect.emit(MyPageEffect.NavigateToMyReviews) }
                }

                MyPageIntent.ClickTasteAnalysis -> {
                    viewModelScope.launch { _effect.emit(MyPageEffect.NavigateToTasteAnalysis) }
                }

                MyPageIntent.ClickSettings -> {
                    viewModelScope.launch { _effect.emit(MyPageEffect.NavigateToSettings) }
                }

                MyPageIntent.ClickLogout -> logout()
            }
        }

        private fun logout() {
            sessionManager.logout()
        }
    }
