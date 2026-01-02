package com.arttrip.android.presentation.exhibition.sub.reviewwrite

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppButton
import com.arttrip.android.core.ui.component.button.AppButtonDefaults
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.skeleton.StaticSkeleton
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable
import com.arttrip.android.presentation.exhibition.sub.reviewwrite.contract.ReviewWriteIntent
import com.arttrip.android.presentation.exhibition.sub.reviewwrite.contract.ReviewWriteState
import com.arttrip.android.presentation.exhibition.sub.reviewwrite.ui.ReviewPhotoRow
import com.arttrip.android.presentation.exhibition.sub.reviewwrite.ui.ReviewTextField

@Composable
fun ReviewWriteScreen(
    innerPadding: PaddingValues,
    state: ReviewWriteState,
    onIntent: (ReviewWriteIntent) -> Unit,
) {
    val scrollState = rememberScrollState()

    val buttonBottomMargin = 10.dp
    val bottomInset = AppButtonDefaults.Height + buttonBottomMargin
    val bottomContentPadding = 8.dp

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(AppColor.Gray0)
                .padding(innerPadding)
                .padding(bottom = buttonBottomMargin),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(bottom = bottomInset),
            horizontalAlignment = Alignment.Start,
        ) {
            ReviewWriteTopBar(
                onClose = { onIntent(ReviewWriteIntent.BackClicked) },
            )

            ExhibitionHeader(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 12.dp, end = 24.dp, bottom = 11.dp),
                isLoading = false,
                posterUrl = state.posterUrl,
                title = state.title,
                hallName = state.hallName,
            )

            HorizontalDivider(color = AppColor.Gray50, thickness = 1.dp)

            Spacer(modifier = Modifier.height(16.dp))

            VisitDateSection(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                valueText = state.visitDateText,
                onClick = { onIntent(ReviewWriteIntent.VisitDateClicked) },
                onCalendarClick = { onIntent(ReviewWriteIntent.CalendarClicked) },
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                modifier = Modifier.padding(horizontal = 24.dp),
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextPrimary,
                text =
                    buildAnnotatedString {
                        append("사진 첨부 ")
                        withStyle(SpanStyle(color = AppColor.TextTertiary)) {
                            append("(사진은 최대 4개까지 등록가능합니다.)")
                        }
                    },
            )

            Spacer(modifier = Modifier.height(8.dp))

            ReviewPhotoRow(
                photoUris = state.photoUris,
                modifier = Modifier.fillMaxWidth(),
                onUploadClick = {
                    if (!state.canAddPhoto) return@ReviewPhotoRow
                    onIntent(ReviewWriteIntent.AddPhotoClicked)
                },
                onRemoveClick = { index ->
                    onIntent(ReviewWriteIntent.RemovePhotoClicked(index))
                },
                onPhotoClick = { /* TODO: preview */ },
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "리뷰 작성",
                modifier = Modifier.padding(horizontal = 24.dp),
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextPrimary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            ReviewTextField(
                value = state.reviewText,
                onValueChange = { onIntent(ReviewWriteIntent.ReviewTextChanged(it)) },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                maxChars = state.maxTextLength,
            )

            Spacer(modifier = Modifier.height(bottomContentPadding))
        }
        AppButton(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                    ),
            text = "등록하기",
            onClick = { onIntent(ReviewWriteIntent.SubmitClicked) },
            enabled = state.canSubmit,
        )
    }
}

@Composable
private fun ReviewWriteTopBar(onClose: () -> Unit) {
    AppTopBar(
        leading = {
            AppIconButton(
                iconResId = R.drawable.ic_close_24,
                contentDescription = "닫기",
            ) { onClose() }
        },
        title = "리뷰 작성",
    )
}

@Composable
private fun ExhibitionHeader(
    modifier: Modifier,
    isLoading: Boolean,
    posterUrl: String?,
    title: String,
    hallName: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ExhibitionPosterImage(
            modifier = Modifier.size(50.dp),
            url = posterUrl,
            isLoading = isLoading,
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = AppTextStyle.Title02Bold,
                color = AppColor.TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = hallName,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun VisitDateSection(
    modifier: Modifier = Modifier,
    valueText: String?, // 선택됐으면 "2026.01.02" 같은 텍스트,
    onClick: () -> Unit,
    onCalendarClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "방문일",
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
        )

        Spacer(modifier = Modifier.height(8.dp))

        val shape = RoundedCornerShape(8.dp)

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(shape)
                    .border(width = 1.dp, color = AppColor.Gray100, shape = shape)
                    .noRippleClickable(true) { onClick() }
                    .padding(horizontal = 16.dp),
        ) {
            Row(
                modifier = Modifier.matchParentSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val hasValue = !valueText.isNullOrBlank()
                Text(
                    modifier = Modifier.weight(1f),
                    text = if (hasValue) valueText!! else "방문일을 선택하세요.",
                    style = AppTextStyle.Body01Regular,
                    color = if (hasValue) AppColor.TextPrimary else AppColor.TextTertiary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                AppIconButton(
                    iconResId = R.drawable.ic_calendar_24,
                    onIconClick = onCalendarClick,
                )
            }
        }
    }
}

@Composable
private fun ExhibitionPosterImage(
    modifier: Modifier = Modifier,
    url: String?,
    isLoading: Boolean = true,
) {
    if (url.isNullOrEmpty() || isLoading) {
        StaticSkeleton(modifier = modifier)
        return
    }

    Box(modifier = modifier) {
        SubcomposeAsyncImage(
            modifier = Modifier.matchParentSize(),
            model = url,
            contentDescription = "전시 포스터",
            contentScale = ContentScale.Crop,
            loading = { StaticSkeleton(modifier = Modifier.matchParentSize()) },
            error = { StaticSkeleton(modifier = Modifier.matchParentSize()) },
        )
    }
}
