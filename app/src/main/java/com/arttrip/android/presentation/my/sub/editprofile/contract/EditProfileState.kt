package com.arttrip.android.presentation.my.sub.editprofile.contract

data class EditProfileState(
    val userName: String = "사용자",
    val profileImageUrl: String? = null,
    val isUploadingProfileImage: Boolean = false,
    val email: String = "abcd1234@naver.com",
    val isImageSheetVisible: Boolean = false,
    val isNicknameModalVisible: Boolean = false,
    val nicknameInput: String = "",
    val nicknameHelperText: String? = null,
    val isNicknameChecking: Boolean = false,
)
