package com.arttrip.android.presentation.mypage.sub.editprofile.contract

sealed interface EditProfileEffect {
    data object NavigateBack : EditProfileEffect

    data object LaunchAlbumPicker : EditProfileEffect

    data object LaunchCamera : EditProfileEffect

    data class ShowToast(
        val message: String,
    ) : EditProfileEffect
}
