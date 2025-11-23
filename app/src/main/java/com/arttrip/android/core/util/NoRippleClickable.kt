package com.arttrip.android.core.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * Ripple(Indication) 없이 클릭만 처리하는 Modifier 확장 함수.
 *
 * - indication = null 로 리플 제거
 * - interactionSource 를 remember 해서 recomposition 시 불필요한 객체 생성 방지
 *
 * @param enabled 클릭 가능 여부
 * @param onClick 클릭 핸들러
 */
fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: () -> Unit,
): Modifier =
    composed {
        val interactionSource = remember { MutableInteractionSource() }
        this.clickable(
            enabled = enabled,
            indication = null,
            interactionSource = interactionSource,
            onClick = onClick,
        )
    }
