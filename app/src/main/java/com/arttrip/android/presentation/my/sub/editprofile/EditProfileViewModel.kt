package com.arttrip.android.presentation.my.sub.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.presentation.my.sub.editprofile.contract.EditProfileEffect
import com.arttrip.android.presentation.my.sub.editprofile.contract.EditProfileIntent
import com.arttrip.android.presentation.my.sub.editprofile.contract.EditProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel
    @Inject
    constructor() : ViewModel() {
        private val _state = MutableStateFlow(EditProfileState())
        val state = _state.asStateFlow()

        private val _effect = MutableSharedFlow<EditProfileEffect>()
        val effect = _effect.asSharedFlow()

        fun onIntent(intent: EditProfileIntent) {
            when (intent) {
                EditProfileIntent.BackClicked -> {
                    viewModelScope.launch { _effect.emit(EditProfileEffect.NavigateBack) }
                }

                EditProfileIntent.ProfileImageClicked -> {
                    _state.update { it.copy(isImageSheetVisible = true) }
                }

                EditProfileIntent.BottomSheetDismissed -> {
                    _state.update { it.copy(isImageSheetVisible = false) }
                }

                EditProfileIntent.PickFromAlbumClicked -> {
                    _state.update { it.copy(isImageSheetVisible = false) }
                    viewModelScope.launch { _effect.emit(EditProfileEffect.LaunchAlbumPicker) }
                }

                EditProfileIntent.TakePhotoClicked -> {
                    _state.update { it.copy(isImageSheetVisible = false) }
                    viewModelScope.launch { _effect.emit(EditProfileEffect.LaunchCamera) }
                }

                EditProfileIntent.RemovePhotoClicked -> {
                    _state.update {
                        it.copy(
                            isImageSheetVisible = false,
                            profileImageUrl = null, // TODO API
                        )
                    }
                }

                is EditProfileIntent.PhotoPickerResult -> {
                    val uri = intent.uri ?: return
                    // TODO API 및 로딩
                    _state.update { it.copy(profileImageUrl = uri.toString()) }
                }

                is EditProfileIntent.CameraResult -> {
                    val uri = intent.uri ?: return
                    // TODO API 및 로딩
                    _state.update { it.copy(profileImageUrl = uri.toString()) }
                }

                EditProfileIntent.NicknameEditClicked -> {
                    _state.update {
                        it.copy(
                            isNicknameDialogVisible = true,
                            nicknameInput = it.userName,
                            nicknameHelperText = null,
                        )
                    }
                }

                EditProfileIntent.NicknameDialogDismissed -> {
                    _state.update {
                        it.copy(
                            isNicknameDialogVisible = false,
                            nicknameHelperText = null,
                        )
                    }
                }

                is EditProfileIntent.NicknameChanged -> {
                    _state.update {
                        it.copy(
                            nicknameInput = intent.value,
                            nicknameHelperText = null,
                        )
                    }
                }

                EditProfileIntent.NicknameConfirmClicked -> {
                    viewModelScope.launch {
                        val nickname = state.value.nicknameInput.trim()

                        val isDuplicated = checkNicknameDuplicate(nickname)

                        if (isDuplicated) {
                            _state.update { it.copy(nicknameHelperText = "이미 사용 중인 닉네임이에요.") }
                            return@launch
                        }

                        _state.update {
                            it.copy(
                                userName = nickname,
                                isNicknameDialogVisible = false,
                                nicknameHelperText = null,
                            )
                        }
                    }
                }
            }
        }

        /**
         * TODO: 실제 API 연동 포인트
         * - true면 중복, false면 사용 가능
         */
        private fun checkNicknameDuplicate(nickname: String): Boolean {
            // 임시: "test"면 중복
            return nickname.equals("test", ignoreCase = true)
        }
    }
