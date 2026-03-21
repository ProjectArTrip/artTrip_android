package com.arttrip.android.presentation.home.sub.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.button.LikeButton
import com.arttrip.android.core.ui.component.chip.RecentSearchChip
import com.arttrip.android.core.ui.component.chip.SuggestionChip
import com.arttrip.android.core.ui.component.input.AppTextField
import com.arttrip.android.core.ui.component.tag.AppTag
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.domain.model.recentsearch.RecentSearch
import com.arttrip.android.domain.model.usertaste.Taste
import com.arttrip.android.presentation.home.ExhibitionImage
import com.arttrip.android.presentation.home.ExhibitionImageCase
import com.arttrip.android.presentation.home.sub.search.contract.SearchIntent
import com.arttrip.android.presentation.home.sub.search.contract.SearchState

@Composable
fun SearchScreen(
    innerPadding: PaddingValues,
    state: SearchState,
    onIntent: (SearchIntent) -> Unit,
    exhibitionList : LazyPagingItems<Exhibition>
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .noRippleClickable {
                    focusManager.clearFocus()
                },
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
            AppTopBar(
                title = "검색하기",
                leading = {
                    AppIconButton(
                        iconResId = R.drawable.ic_back_24,
                        contentDescription = "Back Button",
                        onIconClick = {
                            onIntent(SearchIntent.BackClicked)
                        },
                    )
                },
            )
            Spacer(
                modifier =
                    Modifier
                        .height(16.dp),
            )

            AppTextField(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                value = state.inputText,
                onValueChange = { text ->
                    onIntent(SearchIntent.InputTextChanged(text))
                },
                placeholder = "새로 오픈한 12월 독일 전시가 있어요",
                trailing = {
                    AppIconButton(
                        iconResId = R.drawable.ic_search_24,
                        contentDescription = "Search Button",
                        onIconClick = {
                            focusManager.clearFocus()

                            if (state.inputText.trim().isNotBlank()) {
                                onIntent(SearchIntent.SearchClicked(state.inputText))
                            }
                        },
                    )
                },
            )

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
            ) {
                if (!state.isSearchResultVisible) {
                    SearchIdleContent(
                        state = state,
                        onIntent = onIntent,
                    )

                } else {
                    if (exhibitionList.itemCount == 0 &&
                        exhibitionList.loadState.refresh is LoadState.NotLoading
                    ) {
                        EmptySearchResultContent()
                    } else {
                        SearchResultContent(
                            exhibitions = exhibitionList,
                            onExhibitionClick = { id ->
                                onIntent(SearchIntent.ExhibitionClicked(id))
                            },
                            onLikeClick = { id ->
                                onIntent(SearchIntent.LikeClicked(id))
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchIdleContent(
    state: SearchState,
    onIntent: (SearchIntent) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Spacer(
            modifier =
                Modifier
                    .height(20.dp),
        )
        if (state.recentKeywordList.isNotEmpty()) {
            RecentSearchSection(
                recentKeywordList = state.recentKeywordList,
                onChipClick = { keyword ->
                    onIntent(SearchIntent.RecentKeywordClicked(keyword))
                },
                onDismissClick = { id ->
                    onIntent(SearchIntent.RecentKeywordDismissClicked(id))
                },
                onDeleteAllClick = {
                    onIntent(SearchIntent.DeleteAllClicked)
                },
            )
            Spacer(
                modifier =
                    Modifier
                        .height(32.dp),
            )
        }

        RecommendSearch(
            recommendKeywordList = state.recommendKeywordList,
            onChipClick = { keyword ->
                onIntent(SearchIntent.RecommendKeywordClicked(keyword))
            },
        )
    }
}

@Composable
fun RecentSearchSection(
    recentKeywordList: List<RecentSearch>,
    onChipClick: (String) -> Unit,
    onDismissClick: (Int) -> Unit,
    onDeleteAllClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "최근 검색어",
            style = AppTextStyle.Title02Bold,
            color = AppColor.TextPrimary,
        )
        Text(
            modifier =
                Modifier
                    .noRippleClickable {
                        onDeleteAllClick()
                    },
            text = "전체삭제",
            style = AppTextStyle.Body02Regular,
            color = AppColor.TextPrimary,
        )
    }
    Spacer(
        modifier =
            Modifier
                .height(12.dp),
    )
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        recentKeywordList.forEach { item ->
            RecentSearchChip(
                label = item.content,
                onChipClick = {
                    onChipClick(item.content)
                },
                onDismissClick = {
                    onDismissClick(item.id)
                },
            )
        }
    }
}

@Composable
fun RecommendSearch(
    recommendKeywordList: List<Taste>,
    onChipClick: (String) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Text(
            text = "추천 검색어",
            style = AppTextStyle.Title02Bold,
            color = AppColor.TextPrimary,
        )
        Spacer(
            modifier =
                Modifier
                    .height(12.dp),
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            recommendKeywordList.forEach { taste ->
                SuggestionChip(
                    label = taste.name,
                    onChipClick = {
                        onChipClick(taste.name)
                    },
                )
            }
        }
    }
}

@Composable
fun EmptySearchResultContent() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(
            modifier =
                Modifier
                    .height(56.dp),
        )
        Text(
            text = "검색결과가 없습니다.",
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextSecondary,
        )
    }
}

@Composable
fun SearchResultContent(
    exhibitions: LazyPagingItems<Exhibition>,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(exhibitions.loadState.refresh) {
        if (exhibitions.loadState.refresh is LoadState.Loading) {
            listState.scrollToItem(0)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier
                .height(24.dp)
        )
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(exhibitions.itemCount) { index ->

                exhibitions[index]?.let { exhibition ->

                    ExhibitionItem(
                        exhibition = exhibition,
                        onExhibitionClick = onExhibitionClick,
                        onLikeClick = onLikeClick,
                    )

                }
            }
            item {
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )
            }
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
                text = exhibition.country,
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextPoint,
            )
            Spacer(
                modifier =
                    Modifier
                        .height(4.dp),
            )
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
