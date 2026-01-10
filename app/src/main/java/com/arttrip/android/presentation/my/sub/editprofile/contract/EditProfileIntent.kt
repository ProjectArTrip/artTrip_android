package com.arttrip.android.presentation.my.sub.editprofile.contract

import android.net.Uri

sealed interface EditProfileIntent {
    data object BackClicked : EditProfileIntent

    data object ProfileImageClicked : EditProfileIntent

    data object BottomSheetDismissed : EditProfileIntent

    data object RemovePhotoClicked : EditProfileIntent

    data object PickFromAlbumClicked : EditProfileIntent

    data object TakePhotoClicked : EditProfileIntent

    data class PhotoPickerResult(
        val uri: Uri?,
    ) : EditProfileIntent

    data class CameraResult(
        val uri: Uri?,
    ) : EditProfileIntent

    data object NicknameEditClicked : EditProfileIntent

    data object NicknameDialogDismissed : EditProfileIntent

    data object NicknameConfirmClicked : EditProfileIntent

    data class NicknameChanged(
        val value: String,
    ) : EditProfileIntent
}
