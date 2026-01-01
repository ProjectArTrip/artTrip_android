package com.arttrip.android.presentation.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle

@Composable
fun EmptyRecommendExhibition() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(color = AppColor.SubLightGray)
                .padding(top = 24.dp, bottom = 28.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_empty_data_40),
                contentDescription = "No Data",
                tint = Color.Unspecified,
            )
            Spacer(
                modifier =
                    Modifier
                        .height(8.dp),
            )
            Text(
                text = "오늘의 전시 추천이 존재하지 않습니다.",
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextTertiary,
            )
        }
    }
}

@Composable
fun EmptyPersonalizedExhibition() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(color = AppColor.SubLightGray)
                .padding(top = 24.dp, bottom = 28.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_empty_data_40),
                contentDescription = "No Data",
                tint = Color.Unspecified,
            )
            Spacer(
                modifier =
                    Modifier
                        .height(8.dp),
            )
            Text(
                text = "회원님을 위한 전시 추천이 존재하지 않습니다.",
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextTertiary,
            )
        }
    }
}

@Composable
fun EmptyScheduleExhibition() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(color = AppColor.SubLightGray)
                .padding(top = 24.dp, bottom = 28.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_empty_data_40),
                contentDescription = "No Data",
                tint = Color.Unspecified,
            )
            Spacer(
                modifier =
                    Modifier
                        .height(8.dp),
            )
            Text(
                text = "해당 날짜에 진행중인 전시가 존재하지 않습니다.",
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextTertiary,
            )
        }
    }
}

@Composable
fun EmptyGenreExhibition(genre: ExhibitionGenre) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(color = AppColor.SubLightGray)
                .padding(top = 24.dp, bottom = 28.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_empty_data_40),
                contentDescription = "No Data",
                tint = Color.Unspecified,
            )
            Spacer(
                modifier =
                    Modifier
                        .height(8.dp),
            )
            Text(
                text = "${genre.label} 장르의 전시가 존재하지 않습니다.",
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextTertiary,
            )
        }
    }
}
