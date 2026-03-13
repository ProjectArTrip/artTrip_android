package com.arttrip.android.presentation.my.sub.editprofile

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.core.util.copyToCacheFile
import com.arttrip.android.domain.model.network.ApiError
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.profile.DeleteProfileImageUseCase
import com.arttrip.android.domain.usecase.profile.ObserveProfileUseCase
import com.arttrip.android.domain.usecase.profile.UpdateProfileImageUseCase
import com.arttrip.android.domain.usecase.profile.UpdateUserNicknameUseCase
import com.arttrip.android.presentation.my.sub.editprofile.contract.EditProfileEffect
import com.arttrip.android.presentation.my.sub.editprofile.contract.EditProfileIntent
import com.arttrip.android.presentation.my.sub.editprofile.contract.EditProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    constructor(
        @param:ApplicationContext private val appContext: Context,
        private val observeProfile: ObserveProfileUseCase,
        private val updateUserNicknameUseCase: UpdateUserNicknameUseCase,
        private val updateProfileImageUseCase: UpdateProfileImageUseCase,
        private val deleteProfileImageUseCase: DeleteProfileImageUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(EditProfileState())
        val state = _state.asStateFlow()

        private val _effect = MutableSharedFlow<EditProfileEffect>()
        val effect = _effect.asSharedFlow()

        init {
            viewModelScope.launch {
                observeProfile().collect { profile ->
                    if (profile != null) {
                        _state.update {
                            it.copy(
                                nickname = profile.nickname,
                                profileImageUrl = profile.profileImageUrl,
                                email = profile.email,
                            )
                        }
                    }
                }
            }
        }

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
                    _state.update { it.copy(isImageSheetVisible = false) }
                    deleteProfileImage()
                }

                is EditProfileIntent.HasUri -> {
                    val uri = intent.uri ?: return
                    uploadProfileImage(uri)
                }

                EditProfileIntent.NicknameEditClicked -> {
                    _state.update {
                        it.copy(
                            isNicknameDialogVisible = true,
                            nicknameInput = it.nickname,
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
                                    isNicknameDialogVisible = false,
                                    nicknameHelperText = null,
                                )
                            }
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
                                        nicknameHelperText = "이미 사용 중인 닉네임이에요.",
                                    )
                                }
                            } else {
                                _effect.emit(EditProfileEffect.ShowToast("저장에 실패했습니다. 잠시 후 다시 시도해주세요."))
                                Log.d("EditProfile", "${result.error}")
                                _state.update {
                                    it.copy(
                                        nicknameHelperText = null,
                                        isNicknameDialogVisible = false,
                                        isLoading = false,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        private fun uploadProfileImage(uri: Uri) {
            viewModelScope.launch {
                val file =
                    uri.copyToCacheFile(
                        context = appContext,
                        subDir = "profile_upload",
                        filePrefix = "edit_profile_",
                    )

                if (file == null) {
                    // TODO: 토스트/에러 이펙트
                    return@launch
                }

                updateProfileImageUseCase(file).collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _state.update { it.copy(isLoading = true) }
                        }
                        is ApiResult.Success -> {
                            _state.update { it.copy(isLoading = false) }
                        }
                        is ApiResult.Error -> {
                            _state.update { it.copy(isLoading = false) }
                            _effect.emit(EditProfileEffect.ShowToast("저장에 실패했습니다. 잠시 후 다시 시도해주세요."))
                        }
                    }
                }
            }
        }

        private fun deleteProfileImage() {
            viewModelScope.launch {
                deleteProfileImageUseCase().collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _state.update { it.copy(isLoading = true) }
                        }
                        is ApiResult.Success -> {
                            _state.update { it.copy(isLoading = false) }
                        }
                        is ApiResult.Error -> {
                            _state.update { it.copy(isLoading = false) }
                            _effect.emit(EditProfileEffect.ShowToast("저장에 실패했습니다. 잠시 후 다시 시도해주세요."))
                        }
                    }
                }
            }
        }
    }
