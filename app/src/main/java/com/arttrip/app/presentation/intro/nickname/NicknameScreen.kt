package com.arttrip.app.presentation.intro.nickname

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arttrip.app.core.ui.component.button.AppButton
import com.arttrip.app.core.ui.component.input.AppTextField
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle
import com.arttrip.app.presentation.intro.nickname.contract.NicknameIntent
import com.arttrip.app.presentation.intro.nickname.contract.NicknameState

@Composable
fun NicknameScreen(
    innerPadding: PaddingValues,
    state: NicknameState,
    onIntent: (NicknameIntent) -> Unit,
) {
    val buttonBottomMargin = 16.dp

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(AppColor.Gray0)
                .padding(start = 24.dp, end = 24.dp, top = 32.dp),
    ) {
        Text(
            text =
                buildAnnotatedString {
                    withStyle(AppTextStyle.Body02Bold.toSpanStyle()) { append("1") }
                    withStyle(AppTextStyle.Body02Light.toSpanStyle()) { append("/2") }
                },
            color = AppColor.TextPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))
        NicknameWelcomeSection(
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(24.dp))
        AppTextField(
            value = state.nicknameInput,
            onValueChange = { onIntent(NicknameIntent.NicknameChanged(it.take(10))) },
            isError = state.helperText != null,
            placeholder = "닉네임 입력 (최대 10자)",
        )
        Spacer(modifier = Modifier.weight(1f))
        AppButton(
            text = "다음으로",
            onClick = { onIntent(NicknameIntent.NicknameConfirmClicked) },
            enabled = state.nicknameInput.isNotBlank(),
        )
        Spacer(modifier = Modifier.height(buttonBottomMargin))
    }
}

@Composable
private fun NicknameWelcomeSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text =
                "닉네임을 입력해주세요.",
            style = AppTextStyle.Headline,
            color = AppColor.TextPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text =
                "닉네임은 문자 또는 숫자를 포함해야 하며,\n" +
                    "특수기호만으로는 설정할 수 없습니다.",
            style = AppTextStyle.Body01Light,
            color = AppColor.TextPrimary,
        )
        Spacer(modifier = Modifier.height(7.dp))
        HorizontalDivider(color = AppColor.Gray100, thickness = 1.dp)
    }
}

@Preview(
    name = "NicknameScreen",
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun PreviewNicknameScreen() {
    NicknameScreen(
        innerPadding = PaddingValues(0.dp),
        state = NicknameState(),
        onIntent = {},
    )
}
