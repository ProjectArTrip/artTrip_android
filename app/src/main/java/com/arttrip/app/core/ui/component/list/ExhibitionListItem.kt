package com.arttrip.app.core.ui.component.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.arttrip.app.core.model.enums.exhibition.ExhibitionStatus
import com.arttrip.app.core.ui.component.button.LikeButton
import com.arttrip.app.core.ui.component.image.AppImagePlaceholder
import com.arttrip.app.core.ui.component.image.AppImagePlaceholderType
import com.arttrip.app.core.ui.component.skeleton.StaticSkeleton
import com.arttrip.app.core.ui.component.tag.AppTag
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle
import com.arttrip.app.core.util.noRippleClickable

/**
 * 전시 목록에서 공통으로 사용하는 리스트 아이템.
 * 썸네일(좋아요 버튼 + 상태 태그 포함)과 전시 정보를 가로로 배치.
 *
 * @param location 즐겨찾기에서 사용. 해외 전시는 국가명, 국내 전시는 지역명. 그 외 화면에서는 null.
 * @param period 서버에서 포맷된 문자열 그대로 표시 (예: "2025.03.01 - 2025.06.30")
 */
@Composable
fun ExhibitionListItem(
    modifier: Modifier = Modifier,
    posterUrl: String,
    location: String?,
    title: String,
    hallName: String,
    period: String,
    status: ExhibitionStatus,
    isLiked: Boolean,
    onLikeClick: () -> Unit,
    onItemClick: () -> Unit,
) {
    Row(
        modifier = modifier.noRippleClickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ExhibitionThumbnail(
            posterUrl = posterUrl,
            status = status,
            isLiked = isLiked,
            onLikeClick = onLikeClick,
        )
        Spacer(modifier = Modifier.width(12.dp))
        ExhibitionInfo(
            location = location,
            title = title,
            hallName = hallName,
            period = period,
        )
    }
}

@Composable
private fun ExhibitionThumbnail(
    posterUrl: String,
    status: ExhibitionStatus,
    isLiked: Boolean,
    onLikeClick: () -> Unit,
) {
    Box(modifier = Modifier.size(100.dp)) {
        SubcomposeAsyncImage(
            modifier =
                Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(8.dp)),
            model = posterUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            loading = { StaticSkeleton(modifier = Modifier.matchParentSize()) },
            error = {
                AppImagePlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    type = AppImagePlaceholderType.S100,
                )
            },
        )
        LikeButton(
            modifier =
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 8.dp),
            isSelected = isLiked,
            onClick = onLikeClick,
        )
        AppTag(
            modifier = Modifier.align(Alignment.BottomEnd),
            status = status,
        )
    }
}

@Composable
private fun ExhibitionInfo(
    location: String?,
    title: String,
    hallName: String,
    period: String,
) {
    Column {
        if (location != null) {
            Text(
                text = location,
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextPoint,
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        Text(
            text = title,
            style = AppTextStyle.Title02Bold,
            color = AppColor.TextPrimary,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = hallName,
            style = AppTextStyle.Body02Regular,
            color = AppColor.TextTertiary,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = period,
            style = AppTextStyle.Body02Regular,
            color = AppColor.TextTertiary,
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun ExhibitionListItemWithLocationPreview() {
    ExhibitionListItem(
        modifier = Modifier.padding(16.dp),
        posterUrl = "https://picsum.photos/100/100",
        location = "프랑스",
        title = "모네: 빛을 그리다",
        hallName = "예술의전당 한가람미술관",
        period = "2025.03.01 - 2025.06.30",
        status = ExhibitionStatus.ONGOING,
        isLiked = true,
        onLikeClick = {},
        onItemClick = {},
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun ExhibitionListItemWithoutLocationPreview() {
    ExhibitionListItem(
        modifier = Modifier.padding(16.dp),
        posterUrl = "",
        location = null,
        title = "이중섭: 황소",
        hallName = "국립현대미술관 서울",
        period = "2025.01.15 - 2025.05.15",
        status = ExhibitionStatus.ENDING_SOON,
        isLiked = false,
        onLikeClick = {},
        onItemClick = {},
    )
}
