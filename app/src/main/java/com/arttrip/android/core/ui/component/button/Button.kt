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
 * AppButton 스타일 구분.
 *
 * - [Primary]: Figma `btn` 디자인 컴포넌트
 * - [Secondary]: 모달 CTA(2버튼)에서 사용하는 보조 버튼 스타일(프로젝트 커스텀)
 */
enum class AppButtonVariant {
    Primary,
    Secondary,
}

/**
 * ### Figma: btn(default/disabled)
 *
 * 공통 버튼 컴포넌트.
 * @param variant
 * - 기본값은 [AppButtonVariant.Primary] (Figma `btn`)
 * - [AppButtonVariant.Secondary]는 모달 CTA에서 쓰는 보조 버튼 스타일(커스텀)
 */
@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    variant: AppButtonVariant = AppButtonVariant.Primary,
    onClick: () -> Unit,
    enabled: Boolean,
    text: String,
) {
    val colors =
        when (variant) {
            AppButtonVariant.Primary -> AppButtonDefaults.PrimaryColors
            AppButtonVariant.Secondary -> AppButtonDefaults.SecondaryColors
        }

    val border =
        when (variant) {
            AppButtonVariant.Primary -> null
            AppButtonVariant.Secondary -> AppButtonDefaults.SecondaryBorder
        }
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier =
            modifier
                .fillMaxWidth()
                .height(AppButtonDefaults.Height),
        shape = AppButtonDefaults.Shape,
        colors = colors,
        border = border,
        contentPadding = AppButtonDefaults.ContentPadding,
    ) {
        Text(
            text = text,
            style = AppTextStyle.Title02Bold,
        )
    }
}

internal object AppButtonDefaults {
    val Height = 52.dp
    val Shape = RoundedCornerShape(12.dp)

    val ContentPadding =
        PaddingValues(
            top = 9.dp,
            bottom = 9.dp,
        )

    val PrimaryColors
        @Composable get() =
            ButtonDefaults.buttonColors(
                containerColor = AppColor.Primary300,
                contentColor = AppColor.TextWhite,
                disabledContainerColor = AppColor.Gray100,
                disabledContentColor = AppColor.TextTertiary,
            )

    val SecondaryColors
        @Composable get() =
            ButtonDefaults.buttonColors(
                containerColor = AppColor.Gray0,
                contentColor = AppColor.TextPrimary,
                disabledContainerColor = AppColor.Gray0,
                disabledContentColor = AppColor.TextTertiary,
            )

    val SecondaryBorder: BorderStroke
        @Composable get() = BorderStroke(1.dp, AppColor.Gray100)
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
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(8.dp),
        color = AppColor.Gray0,
        contentColor = AppColor.TextPrimary,
        border = BorderStroke(1.dp, AppColor.Gray100),
        interactionSource = interactionSource,
    ) {
        Row(
            Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text, style = AppTextStyle.Body01Bold)
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
                text = if (appBtnEnabled) "Primary (Enabled)" else "Primary (Disabled)",
                enabled = appBtnEnabled,
                onClick = { appBtnEnabled = !appBtnEnabled },
            )

            AppButton(
                variant = AppButtonVariant.Secondary,
                text = if (appBtnEnabled) "Secondary (Enabled)" else "Secondary (Disabled)",
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
