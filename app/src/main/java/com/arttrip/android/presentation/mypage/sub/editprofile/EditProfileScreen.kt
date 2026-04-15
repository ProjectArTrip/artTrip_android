package com.arttrip.android.presentation.mypage.sub.editprofile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.dialog.AppTwoButtonDialog
import com.arttrip.android.core.ui.component.input.AppTextField
import com.arttrip.android.core.ui.component.sheet.AppBottomSheetTopBar
import com.arttrip.android.core.ui.component.sheet.AppModalBottomSheet
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable
import com.arttrip.android.presentation.mypage.sub.editprofile.contract.EditProfileIntent
import com.arttrip.android.presentation.mypage.sub.editprofile.contract.EditProfileState

@Composable
fun EditProfileScreen(
    innerPadding: PaddingValues,
    state: EditProfileState,
    onIntent: (EditProfileIntent) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(AppColor.Gray0)
                .padding(innerPadding),
    ) {
        AppTopBar(
            leading = {
                AppIconButton(
                    iconResId = R.drawable.ic_back_24,
                    contentDescription = "뒤로가기",
                    onIconClick = {
                        onIntent(EditProfileIntent.BackClicked)
                    },
                )
            },
            title = "내 정보 수정",
        )
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(16.dp))
            ProfileImage(
                url = state.profileImageUrl,
                fallbackResId = R.drawable.ic_profile_80,
                onImageClick = { onIntent(EditProfileIntent.ProfileImageClicked) },
            )
            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "닉네임",
                    style = AppTextStyle.Body01Regular,
                    color = AppColor.TextPrimary,
                )
                AppIconButton(
                    iconResId = R.drawable.ic_more_24,
                    onIconClick = { onIntent(EditProfileIntent.NicknameEditClicked) },
                )
            }
            Spacer(Modifier.height(8.dp))
            AppTextField(
                value = state.nickname,
                readOnly = true,
            )
            Spacer(Modifier.height(24.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "대표 이메일",
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextPrimary,
            )
            Spacer(Modifier.height(8.dp))
            AppTextField(
                value = state.email,
                enabled = false,
            )
        }
        NicknameEditDialog(
            visible = state.isNicknameDialogVisible,
            nickname = state.nicknameInput,
            helperText = state.nicknameHelperText,
            onDismissRequest = { onIntent(EditProfileIntent.NicknameDialogDismissed) },
            onNicknameChange = { onIntent(EditProfileIntent.NicknameChanged(it)) },
            onConfirmClicked = { onIntent(EditProfileIntent.NicknameConfirmClicked) },
        )
        ProfileImageEditBottomSheet(
            visible = state.isImageSheetVisible,
            onDismissRequest = { onIntent(EditProfileIntent.BottomSheetDismissed) },
            onPickFromAlbumClicked = { onIntent(EditProfileIntent.PickFromAlbumClicked) },
            onRemoveImageClicked = { onIntent(EditProfileIntent.RemovePhotoClicked) },
            onTakePhotoClicked = { onIntent(EditProfileIntent.TakePhotoClicked) },
        )
    }
}

@Composable
private fun ProfileImage(
    modifier: Modifier = Modifier,
    url: String?,
    @DrawableRes fallbackResId: Int,
    onImageClick: () -> Unit = {},
) {
    val avatarSize = 96.dp

    @Composable
    fun AvatarFallback(desc: String) {
        Image(
            painter = painterResource(fallbackResId),
            contentDescription = desc,
            modifier =
                Modifier
                    .size(avatarSize)
                    .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
    }

    Box(
        modifier =
            modifier
                .size(avatarSize)
                .noRippleClickable(
                    onClick = onImageClick,
                ),
    ) {
        if (url.isNullOrBlank()) {
            Box(Modifier.align(Alignment.Center)) { AvatarFallback("프로필 이미지(기본)") }
        } else {
            SubcomposeAsyncImage(
                model = url,
                contentDescription = "프로필 이미지",
                modifier =
                    Modifier
                        .align(Alignment.Center)
                        .size(avatarSize)
                        .clip(CircleShape),
                contentScale = ContentScale.Crop,
                loading = { AvatarFallback("프로필 이미지(로딩)") },
                error = { AvatarFallback("프로필 이미지(에러)") },
            )
        }
        Icon(
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 4.dp, end = 4.dp),
            painter = painterResource(R.drawable.ic_profile_menu_24),
            tint = Color.Unspecified,
            contentDescription = null,
        )
    }
}

@Composable
private fun NicknameEditDialog(
    visible: Boolean,
    nickname: String,
    helperText: String?,
    onDismissRequest: () -> Unit,
    onNicknameChange: (String) -> Unit,
    onConfirmClicked: () -> Unit,
) {
    AppTwoButtonDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        primaryText = "변경하기",
        onPrimaryClick = onConfirmClicked,
        primaryEnabled = nickname.isNotBlank(),
        secondaryText = "취소",
        onSecondaryClick = onDismissRequest,
        secondaryEnabled = true,
        contentBottomPadding = 20.dp,
    ) {
        Text(
            "닉네임을 변경하시겠습니까?",
            style = AppTextStyle.Title02Bold,
            color = AppColor.TextPrimary,
        )
        Spacer(Modifier.height(16.dp))
        AppTextField(
            value = nickname,
            onValueChange = { onNicknameChange(it.take(10)) },
            isError = helperText != null,
            placeholder = "닉네임 입력 (최대 10자)",
        )
        Spacer(Modifier.height(4.dp))
        Text(
            modifier = Modifier.fillMaxWidth().heightIn(min = 14.dp),
            text = helperText.orEmpty(),
            style = AppTextStyle.Body02Light,
            color = if (helperText != null) AppColor.SubRed else Color.Transparent,
        )
        Spacer(Modifier.height(10.dp))
    }
}

@Composable
private fun ProfileImageEditBottomSheet(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    onPickFromAlbumClicked: () -> Unit,
    onRemoveImageClicked: () -> Unit,
    onTakePhotoClicked: () -> Unit,
) {
    AppModalBottomSheet(
        visible = visible,
        onDismissRequest = onDismissRequest,
        topBar = AppBottomSheetTopBar.Header("프로필 이미지 변경"),
    ) {
        Spacer(Modifier.height(12.dp))
        BottomSheetActionItem(
            text = "사진 가져오기",
            onClick = onPickFromAlbumClicked,
        )
        BottomSheetActionItem(
            text = "사진 촬영",
            onClick = onTakePhotoClicked,
        )
        BottomSheetActionItem(
            text = "이미지 삭제하기",
            onClick = onRemoveImageClicked,
            isDestructive = true,
            showDivider = false,
        )
    }
}

@Composable
private fun BottomSheetActionItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false,
    showDivider: Boolean = true,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .noRippleClickable(onClick = onClick),
    ) {
        Spacer(Modifier.height(16.dp))

        Text(
            text = text,
            style = AppTextStyle.Body01Regular,
            color = if (isDestructive) AppColor.SubRed else AppColor.TextPrimary,
        )
        if (showDivider) {
            Spacer(Modifier.height(15.dp))
            HorizontalDivider(thickness = 1.dp, color = AppColor.Gray100)
        } else {
            Spacer(Modifier.height(16.dp))
        }
    }
}
