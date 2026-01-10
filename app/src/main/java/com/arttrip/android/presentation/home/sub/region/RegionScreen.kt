package com.arttrip.android.presentation.home.sub.region

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.ExhibitionStatus
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.button.LikeButton
import com.arttrip.android.core.ui.component.tag.AppTag
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable
import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import com.arttrip.android.presentation.home.ExhibitionImage
import com.arttrip.android.presentation.home.ExhibitionImageCase
import com.arttrip.android.presentation.home.sub.region.contract.RegionIntent
import com.arttrip.android.presentation.home.sub.region.contract.RegionState

@Composable
fun RegionScreen(
    innerPadding: PaddingValues,
    state: RegionState,
    onIntent: (RegionIntent) -> Unit
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
        ) {
            Spacer(
                modifier =
                    Modifier
                        .height(16.dp),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(vertical = 12.dp),
            ) {
                AppIconButton(
                    modifier = Modifier
                        .align(Alignment.CenterStart),
                    iconResId = R.drawable.ic_back_24,
                    contentDescription = "Back Button",
                    onIconClick = {
                        onIntent(RegionIntent.BackIconClicked)
                    }
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "경기",
                        style = AppTextStyle.Headline,
                        color = AppColor.TextPrimary
                    )
                    Spacer(
                        modifier = Modifier
                            .width(4.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_down_24),
                        contentDescription = "DropDown Button"
                    )
                }
            }
            ExhibitionList()
        }
    }
}

@Composable
fun ExhibitionList(
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
    ) {
        val dummyExhibition =
            ExhibitionModel(
                id = 1,
                title = "DDP 매거진 라이브러리: 기록에 머물다",
                posterUrl = "https://i.pinimg.com/originals/5d/90/1f/5d901f30a1ee270123e19b1404165113.jpg",
                status = ExhibitionStatus.ONGOING,
                period = "2025.01.01 - 2025.12.31",
                hallName = "DDP 동대문디자인플라자",
                place = "서울 · 중구",
                isBookmarked = true,
            )
        Spacer(
            modifier =
                Modifier
                    .height(12.dp),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            repeat(10) {
                ExhibitionItem(
                    exhibition = dummyExhibition,
                    onExhibitionClick = { id ->
                        onExhibitionClick(id)
                    },
                    onLikeClick = { id ->
                        onLikeClick(id)
                    }
                )
            }
        }
        Spacer(
            modifier =
                Modifier
                    .height(24.dp),
        )
    }
}

@Composable
fun ExhibitionItem(
    exhibition: ExhibitionModel,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .noRippleClickable {
                    onExhibitionClick(exhibition.id)
                },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ExhibitionImage(
            url = exhibition.posterUrl,
            case = ExhibitionImageCase.CASE3,
        ) {
            LikeButton(
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = (8).dp),
                isSelected = exhibition.isBookmarked,
            ) {
                onLikeClick(exhibition.id)
            }
            AppTag(
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd),
                status = exhibition.status,
            )
        }
        Spacer(
            modifier =
                Modifier
                    .width(12.dp),
        )
        Column {
            Text(
                text = exhibition.title,
                style = AppTextStyle.Body01Bold,
                color = AppColor.TextPrimary,
            )
            Spacer(
                modifier =
                    Modifier
                        .height(4.dp),
            )
            Text(
                text = exhibition.hallName,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextTertiary,
            )
            Spacer(
                modifier =
                    Modifier
                        .height(2.dp),
            )
            Text(
                text = exhibition.period,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextTertiary,
            )
        }
    }
}