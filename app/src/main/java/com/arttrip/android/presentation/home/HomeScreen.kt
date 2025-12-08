package com.arttrip.android.presentation.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.arttrip.android.core.ui.component.button.AppFilterChip
import com.arttrip.android.core.ui.component.button.AppFilterChipCase
import com.arttrip.android.core.ui.component.calendar.DayChipCase01
import com.arttrip.android.core.ui.component.calendar.DayChipStateCase01
import com.arttrip.android.core.ui.component.tab.AppTabCase
import com.arttrip.android.core.ui.component.tab.AppTabRow
import com.arttrip.android.core.ui.component.tag.AppTag
import com.arttrip.android.core.ui.component.tag.AppTagType
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.presentation.home.contract.HomeIntent
import com.arttrip.android.presentation.home.contract.HomeState
import java.time.DayOfWeek
import java.time.LocalDate

enum class ExhibitionTab {
    International, // 해외전시
    Domestic, // 국내전시
}

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    uiState: HomeState,
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

        HomeContainer(
            uiState = uiState,
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
                    .padding(start = 24.dp, end = 18.dp, top = 8.dp, bottom = 8.dp),
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
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                AppBarIconButton(
                    iconRes = R.drawable.ic_alert_24,
                    contentDescription = "알림",
                    onClick = {},
                )
                AppBarIconButton(
                    iconRes = R.drawable.ic_calendar_24,
                    contentDescription = "달력",
                    onClick = {
                        onIntent(HomeIntent.DateFilterIconClicked)
                    },
                )
                AppBarIconButton(
                    iconRes = R.drawable.ic_search_24,
                    contentDescription = "검색",
                    onClick = {},
                )
            }
        }
    }
}

@Composable
fun AppBarIconButton(
    @DrawableRes iconRes: Int,
    contentDescription: String?,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier.size(36.dp),
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = contentDescription,
        )
    }
}

@Composable
fun HomeContainer(
    uiState: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    var selectedTab by remember { mutableStateOf(ExhibitionTab.International) }
    val tabs = remember { ExhibitionTab.entries }

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
            tabs =
                tabs.map {
                    when (it) {
                        ExhibitionTab.International -> "해외전시"
                        ExhibitionTab.Domestic -> "국내전시"
                    }
                },
            selectedIndex = tabs.indexOf(selectedTab),
            onTabSelected = { index ->
                selectedTab = tabs[index]
            },
            modifier = Modifier.padding(start = 24.dp),
        )

        when (selectedTab) {
            ExhibitionTab.International -> InternationalExhibitionSection()
            ExhibitionTab.Domestic -> DomesticExhibitionSection()
        }
    }
}

@Composable
fun InternationalExhibitionSection() {
    val internalExhibits =
        getDummyExhibitList(10, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRv5Sx2WM6VTB5Pdkze2mUgIQ285NCWUw8K6A&s")
    val recommendationExhibits =
        getDummyExhibitList(10, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRv5Sx2WM6VTB5Pdkze2mUgIQ285NCWUw8K6A&s")
    val weeklyExhibits =
        getDummyExhibitList(3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRv5Sx2WM6VTB5Pdkze2mUgIQ285NCWUw8K6A&s")
    val genreExhibit =
        getDummyExhibitList(8, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRv5Sx2WM6VTB5Pdkze2mUgIQ285NCWUw8K6A&s")

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
    ) {
        Spacer(
            modifier = Modifier.height(16.dp),
        )
        CountryChipList()
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
            internalExhibits.forEachIndexed { index, exhibit ->
                ExhibitItemCase1(exhibit = exhibit)
                if (index == 9) {
                    Spacer(
                        modifier =
                            Modifier
                                .width(16.dp),
                    )
                }
            }
        }
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
        PersonalRecommendedSection(
            name = "손현준",
            exhibitList = recommendationExhibits,
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
        ExhibitionByGenreSection(exhibitList = genreExhibit)
    }
}

@Composable
fun CountryChipList() {
    val countryList by remember { mutableStateOf(listOf("전체", "프랑스", "독일", "미국", "호주", "일본", "이탈리아")) }
    var selectedCountry by remember { mutableStateOf("전체") }

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

        countryList.forEach { country ->
            AppFilterChip(
                modifier = Modifier,
                case = AppFilterChipCase.Case02,
                text = country,
                selected = selectedCountry == country,
            ) {
                selectedCountry = country
            }
        }

        Spacer(
            modifier =
                Modifier
                    .width(16.dp),
        )
    }
}

@Composable
fun DomesticExhibitionSection() {
    val internalExhibits = getDummyExhibitList(10, "https://i.pinimg.com/originals/5d/90/1f/5d901f30a1ee270123e19b1404165113.jpg")
    val recommendationExhibits = getDummyExhibitList(10, "https://i.pinimg.com/originals/5d/90/1f/5d901f30a1ee270123e19b1404165113.jpg")
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
                    .height(24.dp),
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
            internalExhibits.forEachIndexed { index, exhibit ->
                ExhibitItemCase1(exhibit = exhibit)
                if (index == 9) {
                    Spacer(
                        modifier =
                            Modifier
                                .width(16.dp),
                    )
                }
            }
        }
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
        PersonalRecommendedSection(
            name = "손현준",
            exhibitList = recommendationExhibits,
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
        ExhibitionByGenreSection(exhibitList = genreExhibit)
    }
}

@Composable
fun ExhibitByLocationSection() {
    val locationList by remember { mutableStateOf(listOf("수도권", "강원권", "충청북부권", "충청남부권", "전라북부권", "전라남부권", "경상북부권", "경상남부권", "제주권")) }

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
            locationList.forEachIndexed { index, location ->
                if (index == 0) {
                    Spacer(
                        modifier =
                            Modifier
                                .width(16.dp),
                    )
                }
                LocationItem(
                    url = "https://img1.yna.co.kr/photo/yna/YH/2011/11/12/PYH2011111201190005600_P4.jpg",
                    name = location,
                )
                if (index == locationList.lastIndex) {
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
fun LocationItem(
    url: String,
    name: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = url,
            contentDescription = "Locaiton Image",
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
fun PersonalRecommendedSection(
    name: String,
    exhibitList: List<ExhibitInfoModel>,
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
        GenreTitle(title = "이번주 전시 일정")

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
            exhibitList.forEach { exhibit ->
                ExhibitItemCase3(exhibit = exhibit)
            }
        }
    }
}

@Composable
fun ExhibitionByGenreSection(exhibitList: List<ExhibitInfoModel>) {
    val genreList by remember { mutableStateOf(listOf<String>("팝아트", "현대미술", "사진전", "타이틀")) }
    var selectedGenre by remember { mutableStateOf("팝아트") }
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        GenreTitle(
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
            genreList.forEachIndexed { index, genre ->
                if (index == 0) {
                    Spacer(
                        modifier =
                            Modifier
                                .width(16.dp),
                    )
                }
                AppFilterChip(
                    case = AppFilterChipCase.Case02,
                    text = genre,
                    selected = selectedGenre == genre,
                ) {
                    selectedGenre = genre
                }
                if (index == genreList.lastIndex) {
                    Spacer(
                        modifier =
                            Modifier
                                .width(16.dp),
                    )
                }
            }
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
fun GenreTitle(
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
        Icon(
            painter = painterResource(R.drawable.ic_more_24),
            contentDescription = "more button",
        )
    }
}

@Composable
fun ExhibitItemCase1(
    exhibit: ExhibitInfoModel,
    onItemClick: () -> Unit = {},
) {
    ExhibitImage(
        modifier =
            Modifier
                .clickable {
                    onItemClick()
                },
        url = exhibit.url,
        case = ExhibitImageCase.CASE1,
    ) {
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
                text = exhibit.place,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextWhite,
            )
            Text(
                text = exhibit.date,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextWhite,
            )
        }
    }
}

@Composable
fun ExhibitItemCase2(
    exhibit: ExhibitInfoModel,
    onItemClick: () -> Unit = {},
) {
    Column(
        modifier =
            Modifier
                .width(120.dp)
                .clickable {
                    onItemClick()
                },
    ) {
        ExhibitImage(url = exhibit.url, case = ExhibitImageCase.CASE2)
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
    exhibit: ExhibitInfoModel,
    onItemClick: () -> Unit = {},
) {
    Row(
        modifier =
            Modifier
                .clickable {
                    onItemClick()
                },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ExhibitImage(
            url = exhibit.url,
            case = ExhibitImageCase.CASE3,
        ) {
            AppTag(
                type = AppTagType.Ongoing,
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd),
            )
        }
        Spacer(
            modifier =
                Modifier
                    .width(12.dp),
        )
        Column {
            Text(
                text = exhibit.country,
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
                text = exhibit.date,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextTertiary,
            )
        }
    }
}

@Composable
fun ExhibitItemCase4(
    exhibit: ExhibitInfoModel,
    onItemClick: () -> Unit = {},
) {
    Column(
        modifier =
            Modifier
                .width(128.dp)
                .clickable {
                    onItemClick()
                },
    ) {
        ExhibitImage(
            url = exhibit.url,
            case = ExhibitImageCase.CASE4,
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
            text = exhibit.date,
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
            title = "초대박 귀여운 쿼카 전시회 skrr~ #$index",
            place = "쿼카 공원",
            date = "2025.07.29 - 2025.08.10",
            country = "한국",
        )
    }
