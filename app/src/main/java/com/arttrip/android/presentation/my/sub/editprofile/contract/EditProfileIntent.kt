package com.arttrip.android.presentation.my.sub.editprofile.contract

import android.net.Uri

sealed interface EditProfileIntent {
    data object BackClicked : EditProfileIntent

    data object ProfileImageClicked : EditProfileIntent

    data object BottomSheetDismissed : EditProfileIntent

    data object RemovePhotoClicked : EditProfileIntent

    data object PickFromAlbumClicked : EditProfileIntent

    data object TakePhotoClicked : EditProfileIntent

    interface HasUri : EditProfileIntent {
        val uri: Uri?
    }

    data class PhotoPickerResult(
        override val uri: Uri?,
    ) : HasUri

    data class CameraResult(
        override val uri: Uri?,
    ) : HasUri

    data object NicknameEditClicked : EditProfileIntent

    data object NicknameDialogDismissed : EditProfileIntent

    data object NicknameConfirmClicked : EditProfileIntent

    data class NicknameChanged(
        val value: String,
    ) : EditProfileIntent
}
