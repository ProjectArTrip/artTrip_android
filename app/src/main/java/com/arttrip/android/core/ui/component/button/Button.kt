package com.arttrip.android.core.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arttrip.android.R
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.ui.theme.Pretendard

/**
 *### Figma: btn(default/disabled)
 *  - `default` == `enabled`
 *  - `disabled` == `!enabled`
 */
@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean,
    text: String,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier =
            modifier
                .fillMaxWidth()
                .height(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = AppColor.Primary300,
                contentColor = AppColor.TextWhite,
                disabledContainerColor = AppColor.Gray100,
                disabledContentColor = AppColor.TextTertiary,
            ),
        contentPadding =
            PaddingValues(
                top = 9.dp,
                bottom = 9.dp,
            ),
    ) {
        Text(
            text = text,
            style = AppTextStyle.Title02Bold,
        )
    }
}

/**
 *### Figma: btn_review
 */
@Composable
fun ReviewButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        onClick = onClick,
        modifier = modifier.height(34.dp),
        shape = RoundedCornerShape(4.dp),
        color = AppColor.Gray0,
        contentColor = AppColor.TextPrimary,
        border = BorderStroke(1.dp, AppColor.Gray50),
        interactionSource = interactionSource,
    ) {
        Row(
            Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text, style = AppTextStyle.Body02Bold)
        }
    }
}

enum class SocialLoginProvider {
    Kakao,
    Google,
}

@Composable
fun SocialLoginButton(
    modifier: Modifier = Modifier,
    provider: SocialLoginProvider,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    val style = socialLoginStyleOf(provider)

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier =
            modifier
                .fillMaxWidth()
                .height(56.dp),
        shape = RoundedCornerShape(8.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = style.backgroundColor,
                contentColor = style.contentColor,
                disabledContainerColor = AppColor.Gray100,
                disabledContentColor = AppColor.TextTertiary,
            ),
    ) {
        Icon(
            painter = painterResource(id = style.iconRes),
            contentDescription = style.text,
            tint = Color.Unspecified,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = style.text,
            style =
            SocialLoginTextStyle,
        )
    }
}

data class SocialLoginStyle(
    val backgroundColor: Color,
    val contentColor: Color,
    val text: String,
    val iconRes: Int,
)

@Composable
private fun socialLoginStyleOf(provider: SocialLoginProvider): SocialLoginStyle =
    when (provider) {
        SocialLoginProvider.Kakao ->
            SocialLoginStyle(
                backgroundColor = Color(0xFFFEE500),
                contentColor = AppColor.TextPrimary,
                text = "카카오로 로그인",
                iconRes = R.drawable.ic_kakao_24,
            )

        SocialLoginProvider.Google ->
            SocialLoginStyle(
                backgroundColor = AppColor.Gray0,
                contentColor = AppColor.TextPrimary,
                text = "Google로 로그인",
                iconRes = R.drawable.ic_google_24,
            )
    }

private val SocialLoginTextStyle =
    TextStyle(
        fontSize = 16.sp,
        lineHeight = 18.sp,
        fontFamily = Pretendard,
        fontWeight = FontWeight(400),
    )

@Preview(
    name = "All Buttons Preview",
    showBackground = false,
    widthDp = 360,
)
@Composable
private fun PreviewAllButtons_Interactive() {
    var appBtnEnabled by remember { mutableStateOf(true) }

    Surface(
        color = Color.Black,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            AppButton(
                text = if (appBtnEnabled) "Btn (Enabled)" else "Btn (Disabled)",
                enabled = appBtnEnabled,
                onClick = { appBtnEnabled = !appBtnEnabled },
            )

            ReviewButton(
                text = "Btn",
                onClick = { },
            )

            SocialLoginButton(
                provider = SocialLoginProvider.Kakao,
                onClick = { },
            )

            SocialLoginButton(
                provider = SocialLoginProvider.Google,
                onClick = { },
            )
        }
    }
}
