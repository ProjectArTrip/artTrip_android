package com.arttrip.app.core.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role

/**
 * 원형 bounded ripple 이 들어가는 clickable.
 * IconButton 같은 느낌으로 쓰기 좋음.
 */
fun Modifier.circularRippleClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = Role.Button,
    onClick: () -> Unit,
): Modifier =
    composed {
        val interactionSource = remember { MutableInteractionSource() }
        this
            .minimumInteractiveComponentSize()
            .clip(CircleShape)
            .clickable(
                enabled = enabled,
                onClickLabel = onClickLabel,
                role = role,
                indication = ripple(bounded = true),
                interactionSource = interactionSource,
                onClick = onClick,
            )
    }
