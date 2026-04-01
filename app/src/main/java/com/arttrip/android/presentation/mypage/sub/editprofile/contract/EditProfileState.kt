package com.arttrip.android.presentation.mypage.sub.editprofile.contract

data class EditProfileState(
    val isLoading: Boolean = false,
    val nickname: String = "",
    val profileImageUrl: String? = null,
    val isUploadingProfileImage: Boolean = false,
    val email: String = "",
    val isImageSheetVisible: Boolean = false,
    val isNicknameDialogVisible: Boolean = false,
    val nicknameInput: String = "",
    val nicknameHelperText: String? = null,
    val isNicknameChecking: Boolean = false,
)
