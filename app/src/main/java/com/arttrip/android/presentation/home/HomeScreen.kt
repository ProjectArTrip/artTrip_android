package com.arttrip.android.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arttrip.android.R
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.core.ui.component.button.AppFilterChip
import com.arttrip.android.core.ui.component.button.AppFilterChipCase
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.button.LikeButton
import com.arttrip.android.core.ui.component.calendar.DayChipCase01
import com.arttrip.android.core.ui.component.calendar.DayChipStateCase01
import com.arttrip.android.core.ui.component.tab.AppTabCase
import com.arttrip.android.core.ui.component.tab.AppTabRow
import com.arttrip.android.core.ui.component.tag.AppTag
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable
import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import com.arttrip.android.presentation.home.contract.HomeIntent
import com.arttrip.android.presentation.home.contract.HomeState
import java.time.DayOfWeek
import java.time.LocalDate

enum class PlaceTab(
    val title: String,
) {
    Foreign("해외전시"),
    Domestic("국내전시"),
    ;

    companion object {
        val tabs = entries

        fun fromIndex(index: Int): PlaceTab = tabs[index]
    }
}

fun PlaceTab.toIndex(): Int = PlaceTab.tabs.indexOf(this)

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
    ) {
        HomeAppBar(
            onIntent = onIntent,
        )

        HomeBody(
            state = state,
            onIntent = onIntent,
        )
    }
}

@Composable
fun HomeAppBar(onIntent: (HomeIntent) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Spacer(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(16.dp),
        )
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logo_black),
                contentDescription = "로고",
                modifier =
                    Modifier
                        .width(88.dp)
                        .height(28.dp),
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                AppIconButton(
                    modifier = Modifier,
                    iconResId = R.drawable.ic_alert_24,
                    contentDescription = "알림",
                ) {
                    onIntent(HomeIntent.AlertIconClicked)
                }
                AppIconButton(
                    modifier = Modifier,
                    iconResId = R.drawable.ic_calendar_24,
                    contentDescription = "달력",
                ) {
                    onIntent(HomeIntent.DateFilterIconClicked)
                }
                AppIconButton(
                    modifier = Modifier,
                    iconResId = R.drawable.ic_search_24,
                    contentDescription = "검색",
                ) {
                    onIntent(HomeIntent.SearchIconClicked)
                }
            }
        }
    }
}

@Composable
fun HomeBody(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    val selectedIndex = state.placeTabs.toIndex()

    Column(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        Spacer(
            modifier = Modifier.height(16.dp),
        )

        AppTabRow(
            case = AppTabCase.Case03,
            tabs = PlaceTab.tabs.map { it.title },
            selectedIndex = selectedIndex,
            onTabSelected = { index ->
                val tab = PlaceTab.fromIndex(index)
                if (tab != state.placeTabs) onIntent(HomeIntent.SelectTab(tab))
            },
            modifier = Modifier.padding(start = 24.dp),
        )

        // 해외 전시 탭만 국가 리스트 활성화
        if (state.placeTabs == PlaceTab.Foreign) {
            CountryListChip(state.selectedCountry) { country ->
                onIntent(HomeIntent.SelectCountry(country))
            }
        } else {
            Spacer(
                modifier =
                    Modifier
                        .height(8.dp),
            )
        }

        when (state.placeTabs) {
            PlaceTab.Foreign -> InternationalExhibitionSection(state, onIntent)
            PlaceTab.Domestic -> DomesticExhibitionSection(state, onIntent)
        }
    }
}

@Composable
fun CountryListChip(
    selectedCountry: ForeignCountry,
    onCountryClick: (ForeignCountry) -> Unit
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
        Row(
            modifier =
                Modifier
                    .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Spacer(
                modifier =
                    Modifier
                        .width(16.dp),
            )

            ForeignCountry.entries.forEach { country ->
                AppFilterChip(
                    case = AppFilterChipCase.Case02,
                    text = country.label,
                    selected = selectedCountry == country,
                    onClick = {
                        onCountryClick(country)
                    },
                )
            }

            Spacer(
                modifier =
                    Modifier
                        .width(16.dp),
            )
        }
        Spacer(
            modifier =
                Modifier
                    .height(8.dp),
        )
    }
}

@Composable
fun InternationalExhibitionSection(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    val recommendExhibitList = state.countryData.getValue(state.selectedCountry).recommendExhibit
    val personalizedExhibitList = state.countryData.getValue(state.selectedCountry).personalizedList
//    val weeklyExhibits = state.countryData.getValue(state.selectedCountry).weeklyList.getValue()

    val genreChip = state.foreignGenreChips[ForeignCountry.entries.indexOf(state.selectedCountry)]
    val genreExhibit =
        state.countryData
            .getValue(state.selectedCountry)
            .genreList
            .getValue(genreChip)

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
    ) {
        Spacer(
            modifier = Modifier.height(8.dp),
        )
        RecommendSection(recommendExhibitList)
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
        PersonalizedSection(
            name = "손현준",
            exhibitionList = personalizedExhibitList,
        )
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
//        WeeklyExhibitSection(exhibitList = weeklyExhibits)
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
//        ExhibitionByGenreSection(exhibitList = genreExhibit, selectedGenre = genreChip) { genre ->
//            onIntent(HomeIntent.SelectForeignGenre(genre))
//        }
        Spacer(
            modifier =
                Modifier
                    .height(24.dp),
        )
    }
}

@Composable
fun DomesticExhibitionSection(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    val recommendExhibitList = state.domesticRecommendExhibitList
    val personalizedExhibitList = state.domesticPersonalizedExhibitList
    val weeklyExhibits = state.domesticScheduledExhibitList
    val genreExhibit = state.domesticGenreChips

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
    ) {
        Spacer(
            modifier =
                Modifier
                    .height(16.dp),
        )
        RecommendSection(recommendExhibitList)
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
        DomesticRegionSection()
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
        PersonalizedSection(
            name = "손현준",
            exhibitionList = personalizedExhibitList,
        )
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
        WeeklyExhibitSection(exhibitList = weeklyExhibits)
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
//        ExhibitionByGenreSection(exhibitList = genreExhibit, selectedGenre = state.domesticGenreChips) { genre ->
//            onIntent(HomeIntent.SelectDomesticGenre(genre))
//        }
        Spacer(
            modifier =
                Modifier
                    .height(24.dp),
        )
    }
}

@Composable
fun DomesticRegionSection() {
    Column {
        Row {
            Spacer(
                modifier =
                    Modifier
                        .width(24.dp),
            )
            Text(
                text = "지역별 전시",
                style = AppTextStyle.Title01Bold,
                color = AppColor.TextPrimary,
            )
        }
        Spacer(
            modifier =
                Modifier
                    .height(12.dp),
        )
        Row(
            modifier =
                Modifier
                    .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Spacer(
                modifier =
                    Modifier
                        .width(16.dp),
            )

            DomesticRegion.entries.forEach { region ->
                if (region != DomesticRegion.Entire) {
                    DomesticRegionItem(region = region)
                }
            }

            Spacer(
                modifier =
                    Modifier
                        .width(16.dp),
            )
        }
    }
}

@Composable
fun DomesticRegionItem(region: DomesticRegion) {
    val painter =
        painterResource(
            when (region) {
                DomesticRegion.Entire -> R.drawable.img_seoul
                DomesticRegion.Seoul -> R.drawable.img_seoul
                DomesticRegion.Gyeonggi -> R.drawable.img_gyeonggi
                DomesticRegion.Gangwon -> R.drawable.img_gangwon
                DomesticRegion.Chungcheong -> R.drawable.img_chungcheong
                DomesticRegion.Jeolla -> R.drawable.img_jeolla
                DomesticRegion.Gyeongsang -> R.drawable.img_gyeongsang
                DomesticRegion.Jeju -> R.drawable.img_jeju
            },
        )
    val name = region.label

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painter,
            contentDescription = "Region Image",
            modifier =
                Modifier
                    .size(64.dp)
                    .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Spacer(
            modifier =
                Modifier
                    .height(12.dp),
        )
        Text(
            text = name,
            style = AppTextStyle.Body02Bold,
            color = AppColor.TextPrimary,
        )
    }
}

@Composable
fun RecommendSection(exhibitionList: List<ExhibitionModel>) {
    Row(
        modifier =
            Modifier
                .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Spacer(
            modifier =
                Modifier
                    .width(16.dp),
        )
        exhibitionList.forEachIndexed { index, exhibition ->
            ExhibitionItemCase1(exhibition = exhibition,
                onExhibitionClick = {id ->},
                onLikeClick = {id ->})
            if (index == exhibitionList.lastIndex) {
                Spacer(
                    modifier =
                        Modifier
                            .width(16.dp),
                )
            }
        }
    }
}

@Composable
fun PersonalizedSection(
    name: String,
    exhibitionList: List<ExhibitionModel>,
) {
    Column {
        Row {
            Spacer(
                modifier =
                    Modifier
                        .width(24.dp),
            )
            Text(
                text = "${name}님을 위한 추천",
                style = AppTextStyle.Title01Bold,
                color = AppColor.TextPrimary,
            )
        }
        Spacer(
            modifier =
                Modifier
                    .height(12.dp),
        )
        Row(
            modifier =
                Modifier
                    .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            exhibitionList.forEachIndexed { index, exhibition ->
                if (index == 0) {
                    Spacer(
                        modifier =
                            Modifier
                                .width(16.dp),
                    )
                }
                ExhibitionItemCase2(exhibition = exhibition,
                    onExhibitionClick = {id ->},
                    onLikeClick = {id ->})
                if (index == exhibitionList.lastIndex) {
                    Spacer(
                        modifier =
                            Modifier
                                .width(16.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun WeeklyExhibitSection(exhibitList: List<ExhibitionModel>) {
    val weekDates = remember { getThisWeekDates() }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
    ) {
        SectionTitle(title = "이번주 전시 일정",
            onMoreClick = {})

        Spacer(
            modifier =
                Modifier
                    .height(12.dp),
        )

        Row(
            modifier =
                Modifier
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            weekDates.forEach { date ->
                DayChipCase01(
                    dayOfMonth = date.dayOfMonth,
                    dayOfWeek = date.dayOfWeek,
                    state = if (date == selectedDate) DayChipStateCase01.Selected else DayChipStateCase01.Unselected,
                ) {
                    selectedDate = date
                }
            }
        }

        Spacer(
            modifier =
                Modifier
                    .height(20.dp),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            exhibitList.forEach { exhibition ->
                ExhibitItemCase3(exhibition = exhibition,
                    onExhibitionClick = {id ->},
                    onLikeClick = {id ->})
            }
        }
    }
}

private fun getThisWeekDates(): List<LocalDate> {
    val today = LocalDate.now()

    val monday = today.with(DayOfWeek.MONDAY)

    return (0..6).map { offset ->
        monday.plusDays(offset.toLong())
    }
}

@Composable
fun ExhibitionByGenreSection(
    exhibitionList: List<ExhibitionModel>,
    selectedGenre: ExhibitionGenre,
    onGenreClick: (ExhibitionGenre) -> Unit,
    onMoreClick: () -> Unit
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        SectionTitle(
            modifier =
                Modifier
                    .padding(horizontal = 24.dp),
            title = "장르별 전시 추천",
            onMoreClick = onMoreClick
        )

        Spacer(
            modifier =
                Modifier
                    .height(16.dp),
        )

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Spacer(
                modifier =
                    Modifier
                        .width(16.dp),
            )

            ExhibitionGenre.entries.forEach { genre ->
                AppFilterChip(
                    case = AppFilterChipCase.Case02,
                    text = genre.label,
                    selected = selectedGenre == genre,
                ) {
                    onGenreClick(genre)
                }
            }

            Spacer(
                modifier =
                    Modifier
                        .width(16.dp),
            )
        }

        Spacer(
            modifier =
                Modifier
                    .height(16.dp),
        )

        Row(
            modifier =
                Modifier
                    .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            exhibitionList.forEachIndexed { index, exhibition ->
                if (index == 0) {
                    Spacer(
                        modifier =
                            Modifier
                                .width(16.dp),
                    )
                }
                ExhibitionItemCase4(exhibition = exhibition,
                    onExhibitionClick = {id ->

                    },
                    onLikeClick = {id ->

                    })
                if (index == exhibitionList.lastIndex) {
                    Spacer(
                        modifier =
                            Modifier
                                .width(16.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    title: String,
    onMoreClick: () -> Unit
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = AppTextStyle.Title01Bold,
            color = AppColor.TextPrimary,
        )
        AppIconButton(
            modifier = Modifier,
            iconResId = R.drawable.ic_more_24,
            contentDescription = "more button",
        ) {
            onMoreClick()
        }
    }
}

@Composable
fun ExhibitionItemCase1(
    exhibition: ExhibitionModel,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit
) {
    ExhibitionImage(
        modifier =
            Modifier
                .noRippleClickable {
                    onExhibitionClick(exhibition.id)
                },
        url = exhibition.posterUrl,
        case = ExhibitionImageCase.CASE1,
    ) {
        LikeButton(
            modifier =
                Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-16).dp, y = (16).dp),
            isSelected = true,
        ) {
            onLikeClick(exhibition.id)
        }
        Box(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                    .background(
                        brush =
                            Brush.verticalGradient(
                                colors =
                                    listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.7f),
                                    ),
                            ),
                    ),
        )
        Column(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = exhibition.title,
                style = AppTextStyle.Title02Bold,
                color = AppColor.TextWhite,
            )
            Text(
                text = exhibition.hallName,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextWhite,
            )
            Text(
                text = exhibition.period,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextWhite,
            )
        }
    }
}

@Composable
fun ExhibitionItemCase2(
    exhibition: ExhibitionModel,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit
) {
    Column(
        modifier =
            Modifier
                .width(120.dp)
                .noRippleClickable {
                    onExhibitionClick(exhibition.id)
                },
    ) {
        ExhibitionImage(url = exhibition.posterUrl, case = ExhibitionImageCase.CASE2) {
            LikeButton(
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = (8).dp),
                isSelected = true,
            ) {
                onLikeClick(exhibition.id)
            }
        }
        Spacer(
            modifier =
                Modifier
                    .height(8.dp),
        )
        Text(
            text = exhibition.title,
            style = AppTextStyle.Body01Bold,
            color = AppColor.TextPrimary,
        )
    }
}

@Composable
fun ExhibitItemCase3(
    exhibition: ExhibitionModel,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit
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
                isSelected = true,
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
                text = exhibition.place,
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
                text = exhibition.place,
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

@Composable
fun ExhibitionItemCase4(
    exhibition: ExhibitionModel,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit
) {
    Column(
        modifier =
            Modifier
                .width(128.dp)
                .noRippleClickable {
                    onExhibitionClick(exhibition.id)
                },
    ) {
        ExhibitionImage(
            url = exhibition.posterUrl,
            case = ExhibitionImageCase.CASE4,
        ) {
            LikeButton(
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = (8).dp),
                isSelected = true,
            ) {
                onLikeClick(exhibition.id)
            }
        }
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
            style = AppTextStyle.Body03Regular,
            color = AppColor.TextTertiary,
        )
    }
}

enum class ExhibitionImageCase {
    CASE1,
    CASE2,
    CASE3,
    CASE4,
}

@Composable
fun ExhibitionImage(
    modifier: Modifier = Modifier,
    url: String,
    case: ExhibitionImageCase,
    content: (@Composable BoxScope.() -> Unit)? = null,
) {
    val width =
        when (case) {
            ExhibitionImageCase.CASE1 -> 180.dp
            ExhibitionImageCase.CASE2 -> 120.dp
            ExhibitionImageCase.CASE3 -> 100.dp
            ExhibitionImageCase.CASE4 -> 128.dp
        }

    val height =
        when (case) {
            ExhibitionImageCase.CASE1 -> 240.dp
            ExhibitionImageCase.CASE2 -> 150.dp
            ExhibitionImageCase.CASE3 -> 100.dp
            ExhibitionImageCase.CASE4 -> 160.dp
        }

    val borderModifier =
        when (case) {
            ExhibitionImageCase.CASE1 ->
                Modifier.border(
                    width = 1.dp,
                    color = AppColor.Gray50,
                    shape = RoundedCornerShape(8.dp),
                )
            ExhibitionImageCase.CASE2, ExhibitionImageCase.CASE3, ExhibitionImageCase.CASE4 -> Modifier
        }

    Box(
        modifier =
            modifier
                .width(width)
                .height(height),
    ) {
        AsyncImage(
            modifier =
                Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(8.dp))
                    .then(borderModifier),
            model = url,
            contentDescription = "Exhibition Image",
            contentScale = ContentScale.Crop,
        )

        content?.let { slot ->
            slot()
        }
    }
}