package com.arttrip.app.core.ui.component.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arttrip.app.core.ui.theme.AppColor

/**
 * ### 정적인 스켈레톤 UI 컴포넌트
 *
 * 크기(width / height)는 내부에서 강제하지 않으며,
 * 반드시 호출하는 쪽에서 [modifier]를 통해 지정
 *
 * 기본 형태는 사각형([RectangleShape])이며,
 * [shape] 파라미터를 통해 둥근 모서리나 원형 등 다양한 형태로 재사용
 *
 * ### 사용 예시
 * ```kotlin
 * // 사각형 스켈레톤
 * StaticSkeleton(
 *     modifier = Modifier
 *         .fillMaxWidth()
 *         .height(200.dp)
 * )
 *
 * // 둥근 모서리 스켈레톤
 * StaticSkeleton(
 *     modifier = Modifier
 *         .fillMaxWidth()
 *         .height(120.dp),
 *     shape = RoundedCornerShape(8.dp)
 * )
 *
 * // 원형 스켈레톤
 * StaticSkeleton(
 *     modifier = Modifier.size(40.dp),
 *     shape = CircleShape
 * )
 * ```
 */
@Composable
fun StaticSkeleton(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
) {
    Box(
        modifier =
            modifier
                .clip(shape)
                .background(AppColor.Gray100),
    )
}

@Preview(name = "StaticSkeleton - Shapes", showBackground = true)
@Composable
private fun StaticSkeletonPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        StaticSkeleton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(64.dp),
        )

        StaticSkeleton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(64.dp),
            shape = RoundedCornerShape(12.dp),
        )

        StaticSkeleton(
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
        )
    }
}
