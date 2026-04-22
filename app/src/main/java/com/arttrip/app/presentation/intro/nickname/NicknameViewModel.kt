package com.arttrip.app.presentation.intro.nickname

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.app.core.ui.UiMessage
import com.arttrip.app.domain.model.network.ApiError
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.usecase.profile.UpdateUserNicknameUseCase
import com.arttrip.app.presentation.intro.nickname.contract.NicknameEffect
import com.arttrip.app.presentation.intro.nickname.contract.NicknameIntent
import com.arttrip.app.presentation.intro.nickname.contract.NicknameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel
    @Inject
    constructor(
        private val updateUserNicknameUseCase: UpdateUserNicknameUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(NicknameState())
        val state = _state.asStateFlow()

        private val _effect = MutableSharedFlow<NicknameEffect>()
        val effect = _effect.asSharedFlow()

        fun onIntent(intent: NicknameIntent) {
            when (intent) {
                is NicknameIntent.NicknameChanged -> {
                    _state.update {
                        it.copy(
                            nicknameInput = intent.value,
                            helperText = null,
                        )
                    }
                }
                NicknameIntent.NicknameConfirmClicked -> {
                    val nickname = state.value.nicknameInput.trim()
                    submitNicknameChange(nickname)
                }
            }
        }

        private fun submitNicknameChange(nickname: String) {
            viewModelScope.launch {
                updateUserNicknameUseCase(
                    nickname = nickname,
                ).collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = true,
                                )
                            }
                        }
                        is ApiResult.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                )
                            }
                            _effect.emit(NicknameEffect.NavigateToTaste)
                        }
                        is ApiResult.Error -> {
                            val isNicknameConflict =
                                (result.error as? ApiError.HttpError)?.let { e ->
                                    e.statusCode == 409 || e.serverCode == "USER409-CONFLICT"
                                } == true

                            if (isNicknameConflict) {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        helperText = "중복된 닉네임입니다.",
                                    )
                                }
                            } else {
                                _effect.emit(NicknameEffect.ShowToast(UiMessage.ERROR_SAVE_RETRY))
                                Log.d("Nickname", "${result.error}")
                                _state.update {
                                    it.copy(
                                        helperText = null,
                                        isLoading = false,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
