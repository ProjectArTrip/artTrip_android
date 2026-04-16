import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arttrip.app.core.ui.theme.AppColor

/**
 * ### Figma: Toggle
 *
 * - ON/OFF 상태를 전환하는 커스텀 토글 컴포넌트 (track 62x32, thumb 24)
 * - thumb 좌/우 여백은 4dp로 고정
 * - 기본 리플/애니메이션 포함
 *
 * @param checked 현재 토글 상태
 * @param onCheckedChange 상태 변경 콜백
 * @param enabled 토글 클릭 가능 여부
 */
@Composable
fun AppToggle(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
) {
    val trackHeight = 32.dp
    val trackWidth = 62.dp
    val thumbSize = 24.dp

    val checkedTrackColor: Color = AppColor.Primary300
    val uncheckedTrackColor: Color = AppColor.Gray100
    val thumbColor: Color = AppColor.Gray0
    val borderColor: Color = Color.Transparent

    val interactionSource = remember { MutableInteractionSource() }
    val trackShape = RoundedCornerShape(percent = 50)
    val padding = 4.dp
    val maxOffset = trackWidth - thumbSize - padding * 2

    val thumbOffsetX by animateDpAsState(
        targetValue = if (checked) maxOffset else 0.dp,
        animationSpec = tween(durationMillis = 120, easing = FastOutSlowInEasing),
        label = "thumbOffset",
    )

    val trackColor = if (checked) checkedTrackColor else uncheckedTrackColor

    Box(
        modifier =
            modifier
                .size(trackWidth, trackHeight)
                .clip(trackShape)
                .border(1.dp, borderColor, trackShape)
                .background(trackColor, trackShape)
                .toggleable(
                    value = checked,
                    enabled = enabled,
                    role = Role.Switch,
                    interactionSource = interactionSource,
                    indication = null,
                    onValueChange = onCheckedChange,
                ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Box(
            modifier =
                Modifier
                    .offset(x = padding + thumbOffsetX)
                    .size(thumbSize)
                    .clip(CircleShape)
                    .background(thumbColor, CircleShape)
                    .indication(
                        interactionSource = interactionSource,
                        indication = ripple(bounded = false, radius = trackHeight / 2),
                    ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTogglePreview() {
    var checked by remember { mutableStateOf(true) }

    AppToggle(
        checked = checked,
        onCheckedChange = { checked = it },
    )
}
