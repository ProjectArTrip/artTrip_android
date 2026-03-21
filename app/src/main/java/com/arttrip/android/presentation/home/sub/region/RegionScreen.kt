package com.arttrip.android.presentation.home.sub.region

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.button.LikeButton
import com.arttrip.android.core.ui.component.tag.AppTag
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.presentation.home.ExhibitionImage
import com.arttrip.android.presentation.home.ExhibitionImageCase
import com.arttrip.android.presentation.home.sub.region.contract.RegionIntent
import com.arttrip.android.presentation.home.sub.region.contract.RegionState

@Composable
fun RegionScreen(
    innerPadding: PaddingValues,
    state: RegionState,
    onIntent: (RegionIntent) -> Unit,
    exhibitionList: LazyPagingItems<Exhibition>,
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
                    .fillMaxWidth(),
        ) {
            Spacer(
                modifier =
                    Modifier
                        .height(16.dp),
            )
            AppBar(
                state = state,
                onIntent = onIntent,
            )

            ExhibitionList(
                exhibitionList = exhibitionList,
                onExhibitionClick = { id ->
                    onIntent(RegionIntent.ExhibitionClicked(id))
                },
                onLikeClick = { id ->
                    onIntent(RegionIntent.LikeClicked(id))
                },
                expanded = state.isDropdownExpanded,
            )
        }
    }
}

@Composable
fun AppBar(
    state: RegionState,
    onIntent: (RegionIntent) -> Unit,
) {
    var appBarWidthPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current
    val appBarWidthDp = with(density) { appBarWidthPx.toDp() }

    Column {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 11.dp)
                    .onGloballyPositioned { layout ->
                        appBarWidthPx = layout.size.width
                    },
        ) {
            AppIconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                iconResId = R.drawable.ic_back_24,
                contentDescription = "Back Button",
                onIconClick = { onIntent(RegionIntent.BackIconClicked) },
            )

            Row(
                modifier =
                    Modifier
                        .align(Alignment.Center)
                        .noRippleClickable { onIntent(RegionIntent.DropdownClicked) },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = state.selectedRegion.label,
                    style = AppTextStyle.Headline,
                    color = AppColor.TextPrimary,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(if (state.isDropdownExpanded) R.drawable.ic_up_24 else R.drawable.ic_down_24),
                    contentDescription = "DropDown Button",
                )
            }
            DropDown(
                state = state,
                onIntent = onIntent,
                appBarWidthDp = appBarWidthDp,
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = AppColor.Gray100,
        )
    }
}

@Composable
fun DropDown(
    state: RegionState,
    onIntent: (RegionIntent) -> Unit,
    appBarWidthDp: Dp,
) {
    DropdownMenu(
        expanded = state.isDropdownExpanded,
        onDismissRequest = { onIntent(RegionIntent.DropdownDismissed) },
        containerColor = AppColor.Gray0,
        modifier =
            Modifier
                .width(appBarWidthDp + 48.dp)
                .padding(top = 0.dp, bottom = 0.dp),
        offset = DpOffset(x = 0.dp, y = 12.dp),
        shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
        shadowElevation = 0.dp,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            DomesticRegion.entries.forEachIndexed { index, region ->
                val height =
                    if (index == 1 || index == DomesticRegion.entries.lastIndex) {
                        44.dp
                    } else {
                        52.dp
                    }
                if (region != DomesticRegion.Entire) {
                    DropdownMenuItem(
                        contentPadding = PaddingValues(0.dp),
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(height)
                                .background(color = AppColor.Gray0),
                        text = {
                            Column(
                                modifier =
                                    Modifier
                                        .fillMaxWidth(),
                            ) {
                                if (index == DomesticRegion.entries.lastIndex) {
                                    Spacer(
                                        modifier =
                                            Modifier
                                                .height(8.dp),
                                    )
                                }
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = region.label,
                                    style = AppTextStyle.Title01Light,
                                    color = AppColor.TextPrimary,
                                    textAlign = TextAlign.Center,
                                )
                                if (index == 0) {
                                    Spacer(
                                        modifier =
                                            Modifier
                                                .height(8.dp),
                                    )
                                }
                            }
                        },
                        onClick = { onIntent(RegionIntent.RegionSelected(region = region)) },
                    )
                }
            }
        }
    }
}

@Composable
fun ExhibitionList(
    exhibitionList: LazyPagingItems<Exhibition>,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
    expanded: Boolean,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(exhibitionList.loadState.refresh) {
        if (exhibitionList.loadState.refresh is LoadState.Loading) {
            listState.scrollToItem(0)
        }
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        LazyColumn(
            state = listState,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
        ) {
            item { Spacer(modifier = Modifier.height(12.dp)) }

            items(count = exhibitionList.itemCount) { index ->
                exhibitionList[index]?.let { exhibition ->
                    ExhibitionItem(
                        exhibition = exhibition,
                        onExhibitionClick = onExhibitionClick,
                        onLikeClick = onLikeClick,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
        if (expanded) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(color = Color.Black.copy(alpha = 0.6f)),
            )
        }
    }
}

@Composable
fun ExhibitionItem(
    exhibition: Exhibition,
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
