package com.arttrip.app.core.ui.component.toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arttrip.app.R
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle
import com.arttrip.app.core.util.noRippleClickable
import com.arttrip.app.data.local.fcm.FcmMessage
import kotlinx.coroutines.delay

private const val BANNER_DISMISS_DELAY_MS = 4000L

@Composable
fun AppNotificationHost(
    message: FcmMessage?,
    onDismiss: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    LaunchedEffect(message) {
        if (message != null) {
            delay(BANNER_DISMISS_DELAY_MS)
            onDismiss()
        }
    }

    Box(modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        AnimatedVisibility(
            visible = message != null,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically(animationSpec = tween(600)) { -it } + fadeOut(animationSpec = tween(500)),
        ) {
            Surface(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .padding(top = statusBarPadding + 16.dp)
                        .noRippleClickable(enabled = enabled, onClick = onClick)
                        .pointerInput(Unit) {
                            detectVerticalDragGestures { _, dragAmount ->
                                if (dragAmount < 0) onDismiss()
                            }
                        },
                shape = RoundedCornerShape(12.dp),
                color = AppColor.Primary100,
            ) {
                Row(
                    modifier = Modifier.height(64.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "🚀 새로운 소식이 도착했어요",
                        style = AppTextStyle.Body01Bold,
                        color = AppColor.TextPoint,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.width(2.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_24),
                        contentDescription = "more",
                        tint = AppColor.Primary300,
                    )
                }
            }
        }
    }
}
