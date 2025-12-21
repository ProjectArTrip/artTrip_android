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
import com.arttrip.android.core.model.enums.exhibition.ExhibitionStatus
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
            CountryListChip(state.selectedCountry, onIntent)
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
    onIntent: (HomeIntent) -> Unit,
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
                    onClick = { onIntent(HomeIntent.SelectCountry(country)) },
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
    val weeklyExhibits =
        getDummyExhibitList(3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRv5Sx2WM6VTB5Pdkze2mUgIQ285NCWUw8K6A&s")

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
            exhibitList = personalizedExhibitList,
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
        ExhibitionByGenreSection(exhibitList = genreExhibit, selectedGenre = genreChip) { genre ->
            onIntent(HomeIntent.SelectForeignGenre(genre))
        }
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
    val weeklyExhibits = getDummyExhibitList(3, "https://i.pinimg.com/originals/5d/90/1f/5d901f30a1ee270123e19b1404165113.jpg")
    val genreExhibit = getDummyExhibitList(8, "https://i.pinimg.com/originals/5d/90/1f/5d901f30a1ee270123e19b1404165113.jpg")

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
        ExhibitByLocationSection()
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
        PersonalizedSection(
            name = "손현준",
            exhibitList = personalizedExhibitList,
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
fun ExhibitByLocationSection() {
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
                    LocationItem(region = region)
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
fun LocationItem(region: DomesticRegion) {
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
            contentDescription = "Location Image",
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
fun RecommendSection(exhibitList: List<ExhibitionModel>) {
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
        exhibitList.forEachIndexed { index, exhibit ->
            ExhibitItemCase1(exhibit = exhibit)
            if (index == exhibitList.lastIndex) {
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
    exhibitList: List<ExhibitionModel>,
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
            exhibitList.forEachIndexed { index, exhibit ->
                if (index == 0) {
                    Spacer(
                        modifier =
                            Modifier
                                .width(16.dp),
                    )
                }
                ExhibitItemCase2(exhibit = exhibit)
                if (index == exhibitList.lastIndex) {
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
fun WeeklyExhibitSection(exhibitList: List<ExhibitInfoModel>) {
    val weekDates = remember { getThisWeekDates() }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
    ) {
        SectionTitle(title = "이번주 전시 일정")

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

//        Column(
//            verticalArrangement = Arrangement.spacedBy(8.dp),
//        ) {
//            exhibitList.forEach { exhibit ->
//                ExhibitItemCase3(exhibit = exhibit)
//            }
//        }
    }
}

@Composable
fun ExhibitionByGenreSection(
    exhibitList: List<ExhibitionModel>,
    selectedGenre: ExhibitionGenre,
    onGenreClick: (ExhibitionGenre) -> Unit,
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
            exhibitList.forEachIndexed { index, exhibit ->
                if (index == 0) {
                    Spacer(
                        modifier =
                            Modifier
                                .width(16.dp),
                    )
                }
                ExhibitItemCase4(exhibit = exhibit)
                if (index == exhibitList.lastIndex) {
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

private fun getThisWeekDates(): List<LocalDate> {
    val today = LocalDate.now()

    val monday = today.with(DayOfWeek.MONDAY)

    return (0..6).map { offset ->
        monday.plusDays(offset.toLong())
    }
}

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    title: String,
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
        }
    }
}

@Composable
fun ExhibitItemCase1(
    exhibit: ExhibitionModel,
    onItemClick: () -> Unit = {},
) {
    ExhibitImage(
        modifier =
            Modifier
                .noRippleClickable {
                    onItemClick()
                },
        url = exhibit.posterUrl,
        case = ExhibitImageCase.CASE1,
    ) {
        LikeButton(
            modifier =
                Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-16).dp, y = (16).dp),
            isSelected = false,
        ) { }
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
                text = exhibit.title,
                style = AppTextStyle.Title02Bold,
                color = AppColor.TextWhite,
            )
            Text(
                text = exhibit.hallName,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextWhite,
            )
            Text(
                text = exhibit.period,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextWhite,
            )
        }
    }
}

@Composable
fun ExhibitItemCase2(
    exhibit: ExhibitionModel,
    onItemClick: () -> Unit = {},
) {
    Column(
        modifier =
            Modifier
                .width(120.dp)
                .noRippleClickable {
                    onItemClick()
                },
    ) {
        ExhibitImage(url = exhibit.posterUrl, case = ExhibitImageCase.CASE2) {
            LikeButton(
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = (8).dp),
                isSelected = false,
            ) { }
        }
        Spacer(
            modifier =
                Modifier
                    .height(8.dp),
        )
        Text(
            text = exhibit.title,
            style = AppTextStyle.Body01Bold,
            color = AppColor.TextPrimary,
        )
    }
}

@Composable
fun ExhibitItemCase3(
    exhibit: ExhibitionModel,
    onItemClick: () -> Unit = {},
) {
    Row(
        modifier =
            Modifier
                .noRippleClickable {
                    onItemClick()
                },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ExhibitImage(
            url = exhibit.posterUrl,
            case = ExhibitImageCase.CASE3,
        ) {
            LikeButton(
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = (8).dp),
                isSelected = false,
            ) { }
            AppTag(
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd),
                status = ExhibitionStatus.ONGOING,
            )
        }
        Spacer(
            modifier =
                Modifier
                    .width(12.dp),
        )
        Column {
            Text(
                text = exhibit.place,
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextPoint,
            )
            Spacer(
                modifier =
                    Modifier
                        .height(4.dp),
            )
            Text(
                text = exhibit.title,
                style = AppTextStyle.Body01Bold,
                color = AppColor.TextPrimary,
            )
            Spacer(
                modifier =
                    Modifier
                        .height(4.dp),
            )
            Text(
                text = exhibit.place,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextTertiary,
            )
            Spacer(
                modifier =
                    Modifier
                        .height(2.dp),
            )
            Text(
                text = exhibit.period,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextTertiary,
            )
        }
    }
}

@Composable
fun ExhibitItemCase4(
    exhibit: ExhibitionModel,
    onItemClick: () -> Unit = {},
) {
    Column(
        modifier =
            Modifier
                .width(128.dp)
                .noRippleClickable {
                    onItemClick()
                },
    ) {
        ExhibitImage(
            url = exhibit.posterUrl,
            case = ExhibitImageCase.CASE4,
        ) {
            LikeButton(
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = (8).dp),
                isSelected = false,
            ) { }
        }
        Spacer(
            modifier =
                Modifier
                    .height(4.dp),
        )
        Text(
            text = exhibit.title,
            style = AppTextStyle.Body01Bold,
            color = AppColor.TextPrimary,
        )
        Spacer(
            modifier =
                Modifier
                    .height(4.dp),
        )
        Text(
            text = exhibit.hallName,
            style = AppTextStyle.Body02Regular,
            color = AppColor.TextTertiary,
        )
        Spacer(
            modifier =
                Modifier
                    .height(2.dp),
        )
        Text(
            text = exhibit.period,
            style = AppTextStyle.Body03Regular,
            color = AppColor.TextTertiary,
        )
    }
}

enum class ExhibitImageCase {
    CASE1,
    CASE2,
    CASE3,
    CASE4,
}

data class ExhibitInfoModel(
    val url: String,
    val title: String,
    val place: String,
    val date: String,
    val country: String,
)

@Composable
fun ExhibitImage(
    modifier: Modifier = Modifier,
    url: String,
    case: ExhibitImageCase,
    content: (@Composable BoxScope.() -> Unit)? = null,
) {
    val imageWidth =
        when (case) {
            ExhibitImageCase.CASE1 -> 180.dp
            ExhibitImageCase.CASE2 -> 120.dp
            ExhibitImageCase.CASE3 -> 100.dp
            ExhibitImageCase.CASE4 -> 128.dp
        }

    val imageHeight =
        when (case) {
            ExhibitImageCase.CASE1 -> 240.dp
            ExhibitImageCase.CASE2 -> 150.dp
            ExhibitImageCase.CASE3 -> 100.dp
            ExhibitImageCase.CASE4 -> 160.dp
        }

    val borderModifier =
        when (case) {
            ExhibitImageCase.CASE1 ->
                Modifier.border(
                    width = 1.dp,
                    color = AppColor.Gray50,
                    shape = RoundedCornerShape(8.dp),
                )
            ExhibitImageCase.CASE2, ExhibitImageCase.CASE3, ExhibitImageCase.CASE4 -> Modifier
        }

    Box(
        modifier =
            modifier
                .width(imageWidth)
                .height(imageHeight),
    ) {
        AsyncImage(
            modifier =
                Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(8.dp))
                    .then(borderModifier),
            model = url,
            contentDescription = "Exhibit Image",
            contentScale = ContentScale.Crop,
        )

        content?.let { slot ->
            slot()
        }
    }
}

private fun getDummyExhibitList(
    size: Int,
    url: String,
): List<ExhibitInfoModel> =
    List(size) { index ->
        ExhibitInfoModel(
            url = url,
            title = "초대박 귀여운 쿼카 전시회 #$index",
            place = "쿼카 공원",
            date = "2025.07.29 - 2025.08.10",
            country = "한국",
        )
    }
