package com.arttrip.app.presentation.home

import android.os.SystemClock
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.arttrip.app.R
import com.arttrip.app.core.model.enums.domestic.DomesticRegion
import com.arttrip.app.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.core.ui.component.button.AppFilterChip
import com.arttrip.app.core.ui.component.button.AppFilterChipCase
import com.arttrip.app.core.ui.component.button.AppIconButton
import com.arttrip.app.core.ui.component.button.LikeButton
import com.arttrip.app.core.ui.component.calendar.DayChipCase01
import com.arttrip.app.core.ui.component.calendar.DayChipStateCase01
import com.arttrip.app.core.ui.component.chip.CountryChip
import com.arttrip.app.core.ui.component.image.AppImagePlaceholder
import com.arttrip.app.core.ui.component.image.AppImagePlaceholderType
import com.arttrip.app.core.ui.component.skeleton.StaticSkeleton
import com.arttrip.app.core.ui.component.tab.AppTabCase
import com.arttrip.app.core.ui.component.tab.AppTabRow
import com.arttrip.app.core.ui.component.tag.AppTag
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle
import com.arttrip.app.core.util.noRippleClickable
import com.arttrip.app.domain.model.curation.Curation
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.presentation.home.contract.HomeIntent
import com.arttrip.app.presentation.home.contract.HomeState
import com.arttrip.app.presentation.home.model.SectionLoadState
import com.arttrip.app.presentation.home.ui.datefilter.DateFilterBottomSheet
import com.arttrip.app.presentation.home.ui.datefilter.FilterChips
import com.arttrip.app.presentation.home.ui.feedback.CurationSectionLoading
import com.arttrip.app.presentation.home.ui.feedback.ErrorExhibitionList
import com.arttrip.app.presentation.home.ui.feedback.GenreSectionLoading
import com.arttrip.app.presentation.home.ui.feedback.NoGenreExhibition
import com.arttrip.app.presentation.home.ui.feedback.NoPersonalizedExhibition
import com.arttrip.app.presentation.home.ui.feedback.NoRecommendExhibition
import com.arttrip.app.presentation.home.ui.feedback.NoScheduleExhibition
import com.arttrip.app.presentation.home.ui.feedback.RecommendSectionLoading
import com.arttrip.app.presentation.home.ui.feedback.ScheduleSectionLoading
import java.time.DayOfWeek
import java.time.LocalDate
import kotlin.math.abs

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
    bookmarked: Map<Int, Boolean>,
    hasUnread: Boolean = false,
    onIntent: (HomeIntent) -> Unit,
) {
    val foreignScrollState = rememberScrollState()
    var foreignLastScroll by remember { mutableIntStateOf(0) }
    var foreignLastToggleTime by remember { mutableLongStateOf(0L) }

    val domesticScrollState = rememberScrollState()
    var domesticLastScroll by remember { mutableIntStateOf(0) }
    var domesticLastToggleTime by remember { mutableLongStateOf(0L) }

    var appBarVisible by remember { mutableStateOf(true) }

    val thresholdPx = 60
    val debounceMs = 250L

    LaunchedEffect(foreignScrollState) {
        snapshotFlow { foreignScrollState.value }
            .collect { current ->
                val atTop = current == 0
                val atBottom = current == foreignScrollState.maxValue
                if (atTop) appBarVisible = true

                // 양 끝 바운스 구간 흔들림에 의한 애니메이션 방지
                if (atTop || atBottom) {
                    foreignLastScroll = current
                    return@collect
                }

                val delta = current - foreignLastScroll
                if (abs(delta) < thresholdPx) return@collect

                val now = SystemClock.elapsedRealtime()
                if (now - foreignLastToggleTime < debounceMs) {
                    foreignLastScroll = current
                    return@collect
                }

                val scrollingDown = delta > 0
                appBarVisible = !scrollingDown

                foreignLastToggleTime = now
                foreignLastScroll = current
            }
    }

    LaunchedEffect(domesticScrollState) {
        snapshotFlow { domesticScrollState.value }
            .collect { current ->
                val atTop = current == 0
                val atBottom = current == domesticScrollState.maxValue
                if (atTop) appBarVisible = true

                // 양 끝 바운스 구간 흔들림에 의한 애니메이션 방지
                if (atTop || atBottom) {
                    domesticLastScroll = current
                    return@collect
                }

                val delta = current - domesticLastScroll
                if (abs(delta) < thresholdPx) return@collect

                val now = SystemClock.elapsedRealtime()
                if (now - domesticLastToggleTime < debounceMs) {
                    domesticLastScroll = current
                    return@collect
                }

                val scrollingDown = delta > 0
                appBarVisible = !scrollingDown

                domesticLastToggleTime = now
                domesticLastScroll = current
            }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
    ) {
        AnimatedVisibility(
            visible = appBarVisible,
            enter = expandVertically(),
            exit = shrinkVertically(),
        ) {
            HomeAppBar(
                hasUnread = hasUnread,
                onIntent = onIntent,
            )
        }

        HomeBody(
            state = state,
            bookmarked = bookmarked,
            onIntent = onIntent,
            foreignScrollState = foreignScrollState,
            domesticScrollState = domesticScrollState,
        )
    }
}

@Composable
fun HomeAppBar(
    hasUnread: Boolean = false,
    onIntent: (HomeIntent) -> Unit,
) {
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
                    iconResId = if (hasUnread) R.drawable.ic_alert_badge_24 else R.drawable.ic_alert_24,
                    contentDescription = "알림",
                ) {
                    onIntent(HomeIntent.NotificationIconClicked)
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
    bookmarked: Map<Int, Boolean>,
    onIntent: (HomeIntent) -> Unit,
    foreignScrollState: ScrollState,
    domesticScrollState: ScrollState,
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
                onIntent(HomeIntent.CountryClicked(country))
            }
        } else {
            Spacer(
                modifier =
                    Modifier
                        .height(8.dp),
            )
        }

        when (state.placeTabs) {
            PlaceTab.Foreign -> ForeignExhibitionSection(state, bookmarked, onIntent, foreignScrollState)
            PlaceTab.Domestic -> DomesticExhibitionSection(state, bookmarked, onIntent, domesticScrollState)
        }

        DateFilterBottomSheet(
            visible = state.isDateFilterSheetVisible,
            title =
                when (state.placeTabs) {
                    PlaceTab.Foreign -> "국가 및 날짜 선택"
                    PlaceTab.Domestic -> "지역 및 날짜 선택"
                },
            startDate = state.dateFilterStartDate,
            endDate = state.dateFilterEndDate,
            isApplyEnabled =
                when (state.placeTabs) {
                    PlaceTab.Foreign -> state.dateFilterSelectedCountry != null && state.dateFilterEndDate != null
                    PlaceTab.Domestic -> state.dateFilterSelectedRegion != null && state.dateFilterEndDate != null
                },
            locationTitle =
                when (state.placeTabs) {
                    PlaceTab.Foreign -> "국가"
                    PlaceTab.Domestic -> "지역"
                },
            locationDescription =
                when (state.placeTabs) {
                    PlaceTab.Foreign -> state.dateFilterSelectedCountry?.label
                    PlaceTab.Domestic -> state.dateFilterSelectedRegion?.label
                },
            onDayClick = { date -> onIntent(HomeIntent.DateFilterDayClicked(date)) },
            onDateSectionOpen = { onIntent(HomeIntent.DateFilterDateSectionOpened) },
            onResetClick = { onIntent(HomeIntent.DateFilterResetClicked) },
            onApplyClick = { onIntent(HomeIntent.DateFilterApplyClicked) },
            onDismissRequest = { onIntent(HomeIntent.DateFilterSheetDismissed) },
            locationChips = {
                when (state.placeTabs) {
                    PlaceTab.Foreign ->
                        FilterChips(
                            items = ForeignCountry.entries,
                            selected = state.dateFilterSelectedCountry,
                            labelOf = { it.label },
                            onItemClick = { onIntent(HomeIntent.DateFilterCountrySelected(it)) },
                        )
                    PlaceTab.Domestic ->
                        FilterChips(
                            items = DomesticRegion.entries,
                            selected = state.dateFilterSelectedRegion,
                            labelOf = { it.label },
                            onItemClick = { onIntent(HomeIntent.DateFilterRegionSelected(it)) },
                        )
                }
            },
        )
    }
}

@Composable
fun CountryListChip(
    selectedCountry: ForeignCountry,
    onCountryClick: (ForeignCountry) -> Unit,
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
fun ForeignExhibitionSection(
    state: HomeState,
    bookmarked: Map<Int, Boolean>,
    onIntent: (HomeIntent) -> Unit,
    scrollState: ScrollState,
) {
    val selectedCountry = state.selectedCountry
    val curationState = state.foreignCurationData.getValue(selectedCountry)

    val homeSection = state.foreignExhibitionData.getValue(selectedCountry)

    val recommendState = homeSection.recommendExhibit

    val personalizedState = homeSection.personalizedList

    val selectedDate =
        state.foreignSelectedDate[selectedCountry.ordinal]
    val scheduleState: SectionLoadState<List<Exhibition>> =
        homeSection.scheduleList[selectedDate] ?: SectionLoadState.Idle

    val selectedGenre = state.foreignSelectedGenre[selectedCountry.ordinal]
    val genreState =
        homeSection.genreList[selectedGenre] ?: SectionLoadState.Idle

    val hasError =
        recommendState is SectionLoadState.Error ||
            personalizedState is SectionLoadState.Error ||
            scheduleState is SectionLoadState.Error ||
            genreState is SectionLoadState.Error

    if (hasError) {
        ErrorExhibitionList()
        return
    }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
    ) {
        Spacer(
            modifier = Modifier.height(8.dp),
        )
        RecommendSection(
            recommendState,
            bookmarked = bookmarked,
            onExhibitionClick = { id ->
                onIntent(HomeIntent.ExhibitionClicked(id))
            },
            onLikeClick = { id -> onIntent(HomeIntent.ToggleBookmark(id)) },
            placeTab = state.placeTabs,
            foreignCountry = state.selectedCountry,
        )
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
        PersonalizedSection(
            name = state.nickname,
            sectionState = personalizedState,
            bookmarked = bookmarked,
            onExhibitionClick = { id ->
                onIntent(HomeIntent.ExhibitionClicked(id))
            },
            onLikeClick = { id -> onIntent(HomeIntent.ToggleBookmark(id)) },
            placeTab = state.placeTabs,
            foreignCountry = state.selectedCountry,
        )
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
        WeeklyExhibitSection(
            weekDates = getThisWeekDates(),
            selectedDate = selectedDate,
            sectionState = scheduleState,
            bookmarked = bookmarked,
            onMoreClick = {
                onIntent(HomeIntent.ForeignMoreScheduleIconClicked(selectedCountry, selectedDate))
            },
            onDateClick = { date ->
                onIntent(HomeIntent.SelectForeignDate(date))

                onIntent(HomeIntent.LoadForeignScheduledExhibitList(selectedCountry, date))
            },
            onExhibitionClick = { id ->
                onIntent(HomeIntent.ExhibitionClicked(id))
            },
            onLikeClick = { id -> onIntent(HomeIntent.ToggleBookmark(id)) },
        )
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
        CurationSection(
            sectionState = curationState,
            bookmarked = bookmarked,
            placeTab = PlaceTab.Foreign,
            selectedCountry = selectedCountry,
            onMoreClick = { curationId -> onIntent(HomeIntent.CurationMoreClicked(curationId)) },
            onExhibitionClick = { id -> onIntent(HomeIntent.ExhibitionClicked(id)) },
            onLikeClick = { id -> onIntent(HomeIntent.ToggleBookmark(id)) },
        )
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
        ExhibitionByGenreSection(
            selectedGenre = selectedGenre,
            sectionState = genreState,
            bookmarked = bookmarked,
            onGenreClick = { genre ->
                onIntent(HomeIntent.SelectForeignGenre(genre))

                onIntent(HomeIntent.LoadForeignGenreExhibitList(selectedCountry, genre))
            },
            onMoreClick = {
                onIntent(HomeIntent.ForeignMoreGenreIconClicked(selectedCountry, selectedGenre))
            },
            onExhibitionClick = { id ->
                onIntent(HomeIntent.ExhibitionClicked(id))
            },
            onLikeClick = { id -> onIntent(HomeIntent.ToggleBookmark(id)) },
        )
        Spacer(
            modifier =
                Modifier
                    .height(48.dp),
        )
    }
}

private fun getThisWeekDates(): List<LocalDate> {
    val today = LocalDate.now()

    val sunday = today.with(DayOfWeek.MONDAY).minusDays(1)

    return (0..6).map { offset ->
        sunday.plusDays(offset.toLong())
    }
}

@Composable
fun DomesticExhibitionSection(
    state: HomeState,
    bookmarked: Map<Int, Boolean>,
    onIntent: (HomeIntent) -> Unit,
    scrollState: ScrollState,
) {
    val section = state.domesticExhibitionData
    val selectedDate = state.domesticSelectedDate
    val selectedGenre = state.domesticSelectedGenre

    val recommendState = section.recommendExhibit
    val personalizedState = section.personalizedList
    val scheduleState = section.scheduleList[selectedDate] ?: SectionLoadState.Idle
    val genreState = section.genreList[selectedGenre] ?: SectionLoadState.Idle

    val hasError =
        recommendState is SectionLoadState.Error ||
            personalizedState is SectionLoadState.Error ||
            scheduleState is SectionLoadState.Error ||
            genreState is SectionLoadState.Error

    if (hasError) {
        ErrorExhibitionList()
        return
    }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
    ) {
        Spacer(
            modifier =
                Modifier
                    .height(16.dp),
        )
        RecommendSection(
            recommendState,
            bookmarked = bookmarked,
            onExhibitionClick = { id ->
                onIntent(HomeIntent.ExhibitionClicked(id))
            },
            onLikeClick = { id -> onIntent(HomeIntent.ToggleBookmark(id)) },
            placeTab = state.placeTabs,
            foreignCountry = state.selectedCountry,
        )

        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
        PersonalizedSection(
            name = state.nickname,
            sectionState = personalizedState,
            bookmarked = bookmarked,
            onExhibitionClick = { id ->
                onIntent(HomeIntent.ExhibitionClicked(id))
            },
            onLikeClick = { id -> onIntent(HomeIntent.ToggleBookmark(id)) },
            placeTab = state.placeTabs,
            foreignCountry = state.selectedCountry,
        )
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
        WeeklyExhibitSection(
            weekDates = getThisWeekDates(),
            selectedDate = selectedDate,
            sectionState = scheduleState,
            bookmarked = bookmarked,
            onMoreClick = {
                onIntent(HomeIntent.DomesticMoreScheduleIconClicked(selectedDate))
            },
            onDateClick = { date ->
                onIntent(HomeIntent.SelectDomesticDate(date))

                onIntent(HomeIntent.LoadDomesticScheduledExhibitList(DomesticRegion.Entire, date))
            },
            onExhibitionClick = { id ->
                onIntent(HomeIntent.ExhibitionClicked(id))
            },
            onLikeClick = { id -> onIntent(HomeIntent.ToggleBookmark(id)) },
        )
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
        DomesticRegionSection(onRegionClick = { region ->
            onIntent(HomeIntent.RegionClicked(region = region))
        })
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
        CurationSection(
            sectionState = state.domesticCurationData,
            bookmarked = bookmarked,
            placeTab = PlaceTab.Domestic,
            selectedCountry = ForeignCountry.Entire,
            onMoreClick = { curationId -> onIntent(HomeIntent.CurationMoreClicked(curationId)) },
            onExhibitionClick = { id -> onIntent(HomeIntent.ExhibitionClicked(id)) },
            onLikeClick = { id -> onIntent(HomeIntent.ToggleBookmark(id)) },
        )
        Spacer(
            modifier =
                Modifier
                    .height(32.dp),
        )
        ExhibitionByGenreSection(
            selectedGenre = selectedGenre,
            sectionState = genreState,
            bookmarked = bookmarked,
            onGenreClick = { genre ->
                onIntent(HomeIntent.SelectDomesticGenre(genre))

                onIntent(HomeIntent.LoadDomesticGenreExhibitList(DomesticRegion.Entire, genre))
            },
            onMoreClick = {
                onIntent(HomeIntent.DomesticMoreGenreIconClicked(selectedGenre))
            },
            onExhibitionClick = { id ->
                onIntent(HomeIntent.ExhibitionClicked(id))
            },
            onLikeClick = { id -> onIntent(HomeIntent.ToggleBookmark(id)) },
        )
        Spacer(
            modifier =
                Modifier
                    .height(48.dp),
        )
    }
}

@Composable
fun DomesticRegionSection(onRegionClick: (DomesticRegion) -> Unit) {
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
                    DomesticRegionItem(region = region, onRegionClick = { region -> onRegionClick(region) })
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
fun DomesticRegionItem(
    region: DomesticRegion,
    onRegionClick: (DomesticRegion) -> Unit,
) {
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
        modifier =
            Modifier
                .noRippleClickable {
                    onRegionClick(region)
                },
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
fun RecommendSection(
    sectionState: SectionLoadState<List<Exhibition>>,
    bookmarked: Map<Int, Boolean>,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
    placeTab: PlaceTab,
    foreignCountry: ForeignCountry,
) {
    when (sectionState) {
        SectionLoadState.Idle -> {
        }
        SectionLoadState.Loading -> {
            RecommendSectionLoading()
        }
        is SectionLoadState.Success -> {
            if (sectionState.data.isEmpty()) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                ) {
                    NoRecommendExhibition()
                }
            } else {
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
                    sectionState.data.forEach { exhibition ->
                        ExhibitionItemCase1(
                            exhibition = exhibition.copy(isBookmarked = bookmarked[exhibition.id] ?: exhibition.isBookmarked),
                            onExhibitionClick = { id ->
                                onExhibitionClick(id)
                            },
                            onLikeClick = { id ->
                                onLikeClick(id)
                            },
                            placeTab = placeTab,
                            foreignCountry = foreignCountry,
                        )
                    }
                    Spacer(
                        modifier =
                            Modifier
                                .width(16.dp),
                    )
                }
            }
        }
        is SectionLoadState.Error -> {
        }
    }
}

@Composable
fun PersonalizedSection(
    name: String,
    sectionState: SectionLoadState<List<Exhibition>>,
    bookmarked: Map<Int, Boolean>,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
    placeTab: PlaceTab,
    foreignCountry: ForeignCountry,
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
        when (sectionState) {
            SectionLoadState.Idle -> {
            }
            SectionLoadState.Loading -> {
                RecommendSectionLoading()
            }
            is SectionLoadState.Success -> {
                if (sectionState.data.isEmpty()) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                    ) {
                        NoPersonalizedExhibition()
                    }
                } else {
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
                        sectionState.data.forEach { exhibition ->
                            ExhibitionItemCase2(
                                exhibition = exhibition.copy(isBookmarked = bookmarked[exhibition.id] ?: exhibition.isBookmarked),
                                onExhibitionClick = { id ->
                                    onExhibitionClick(id)
                                },
                                onLikeClick = { id ->
                                    onLikeClick(id)
                                },
                                placeTab = placeTab,
                                foreignCountry = foreignCountry,
                            )
                        }
                        Spacer(
                            modifier =
                                Modifier
                                    .width(16.dp),
                        )
                    }
                }
            }
            is SectionLoadState.Error -> {
            }
        }
    }
}

@Composable
fun WeeklyExhibitSection(
    weekDates: List<LocalDate>,
    selectedDate: LocalDate,
    sectionState: SectionLoadState<List<Exhibition>>,
    bookmarked: Map<Int, Boolean>,
    onMoreClick: () -> Unit,
    onDateClick: (LocalDate) -> Unit,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
    ) {
        SectionTitle(
            title = "이번주 전시 일정",
            onMoreClick = {
                onMoreClick()
            },
        )

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
                    onDateClick(date)
                }
            }
        }

        Spacer(
            modifier =
                Modifier
                    .height(20.dp),
        )

        when (sectionState) {
            SectionLoadState.Idle -> {
            }
            SectionLoadState.Loading -> {
                ScheduleSectionLoading()
            }
            is SectionLoadState.Success -> {
                if (sectionState.data.isEmpty()) {
                    NoScheduleExhibition()
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        sectionState.data.forEach { exhibition ->
                            ExhibitItemCase3(
                                exhibition = exhibition.copy(isBookmarked = bookmarked[exhibition.id] ?: exhibition.isBookmarked),
                                onExhibitionClick = { id ->
                                    onExhibitionClick(id)
                                },
                                onLikeClick = { id ->
                                    onLikeClick(id)
                                },
                            )
                        }
                    }
                }
            }
            is SectionLoadState.Error -> {
            }
        }
    }
}

@Composable
fun CurationSection(
    sectionState: SectionLoadState<Curation>,
    bookmarked: Map<Int, Boolean>,
    placeTab: PlaceTab,
    selectedCountry: ForeignCountry,
    onMoreClick: (Long) -> Unit,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
) {
    when (sectionState) {
        SectionLoadState.Idle -> {}
        SectionLoadState.Loading -> {
            CurationSectionLoading()
        }
        is SectionLoadState.Success -> {
            CurationItem(
                curation = sectionState.data,
                bookmarked = bookmarked,
                placeTab = placeTab,
                selectedCountry = selectedCountry,
                onMoreClick = { onMoreClick(sectionState.data.curationId) },
                onExhibitionClick = onExhibitionClick,
                onLikeClick = onLikeClick,
            )
        }
        is SectionLoadState.Error -> {}
    }
}

@Composable
fun CurationItem(
    curation: Curation,
    bookmarked: Map<Int, Boolean>,
    placeTab: PlaceTab,
    selectedCountry: ForeignCountry,
    onMoreClick: () -> Unit,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        SectionTitle(
            modifier = Modifier.padding(horizontal = 24.dp),
            title = curation.title,
            onMoreClick = onMoreClick,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = curation.subtitle,
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            curation.exhibits.forEach { exhibition ->
                ExhibitionItemCase1(
                    exhibition = exhibition.copy(isBookmarked = bookmarked[exhibition.id] ?: exhibition.isBookmarked),
                    onExhibitionClick = onExhibitionClick,
                    onLikeClick = onLikeClick,
                    placeTab = placeTab,
                    foreignCountry = selectedCountry,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
fun ExhibitionByGenreSection(
    selectedGenre: ExhibitionGenre,
    sectionState: SectionLoadState<List<Exhibition>>,
    bookmarked: Map<Int, Boolean>,
    onGenreClick: (ExhibitionGenre) -> Unit,
    onMoreClick: () -> Unit,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
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
            onMoreClick = onMoreClick,
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

        when (sectionState) {
            SectionLoadState.Idle -> {
            }
            SectionLoadState.Loading -> {
                GenreSectionLoading()
            }
            is SectionLoadState.Success -> {
                if (sectionState.data.isEmpty()) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                    ) {
                        NoGenreExhibition(selectedGenre)
                    }
                } else {
                    Column(
                        modifier =
                            Modifier
                                .padding(horizontal = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        sectionState.data.forEach { exhibition ->
                            ExhibitionItemCase4(
                                exhibition = exhibition.copy(isBookmarked = bookmarked[exhibition.id] ?: exhibition.isBookmarked),
                                onExhibitionClick = { id ->
                                    onExhibitionClick(id)
                                },
                                onLikeClick = { id ->
                                    onLikeClick(id)
                                },
                            )
                        }
                    }
                }
            }
            is SectionLoadState.Error -> {
            }
        }
    }
}

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    title: String,
    onMoreClick: () -> Unit,
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
    exhibition: Exhibition,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
    placeTab: PlaceTab,
    foreignCountry: ForeignCountry,
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
        if (placeTab == PlaceTab.Foreign && foreignCountry == ForeignCountry.Entire) {
            CountryChip(
                modifier =
                    Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 10.dp, y = 16.dp),
                label = exhibition.country,
            )
        } else if (placeTab == PlaceTab.Domestic) {
            CountryChip(
                modifier =
                    Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 10.dp, y = 16.dp),
                label = exhibition.region,
            )
        }
        LikeButton(
            modifier =
                Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-16).dp, y = (16).dp),
            isSelected = exhibition.isBookmarked,
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
    exhibition: Exhibition,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
    placeTab: PlaceTab,
    foreignCountry: ForeignCountry,
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
            if (placeTab == PlaceTab.Foreign && foreignCountry == ForeignCountry.Entire) {
                CountryChip(
                    modifier =
                        Modifier
                            .align(Alignment.TopStart)
                            .offset(x = 8.dp, y = 9.dp),
                    label = exhibition.country,
                )
            } else if (placeTab == PlaceTab.Domestic) {
                CountryChip(
                    modifier =
                        Modifier
                            .align(Alignment.TopStart)
                            .offset(x = 8.dp, y = 9.dp),
                    label = exhibition.region,
                )
            }

            LikeButton(
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = (8).dp),
                isSelected = exhibition.isBookmarked,
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
                text = if (exhibition.country == "한국") exhibition.region else exhibition.country,
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

@Composable
fun ExhibitionItemCase4(
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
            ExhibitionImageCase.CASE3, ExhibitionImageCase.CASE4 -> 100.dp
        }

    val height =
        when (case) {
            ExhibitionImageCase.CASE1 -> 240.dp
            ExhibitionImageCase.CASE2 -> 150.dp
            ExhibitionImageCase.CASE3, ExhibitionImageCase.CASE4 -> 100.dp
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

    val placeholderType =
        when (case) {
            ExhibitionImageCase.CASE1 -> AppImagePlaceholderType.P180
            ExhibitionImageCase.CASE2 -> AppImagePlaceholderType.P120
            ExhibitionImageCase.CASE3, ExhibitionImageCase.CASE4 -> AppImagePlaceholderType.S100
        }

    Box(
        modifier =
            modifier
                .width(width)
                .height(height),
    ) {
        SubcomposeAsyncImage(
            modifier =
                Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(8.dp))
                    .then(borderModifier),
            model = url,
            contentDescription = "Exhibition Image",
            contentScale = ContentScale.Crop,
            loading = {
                StaticSkeleton(modifier = Modifier.fillMaxSize())
            },
            error = {
                AppImagePlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    type = placeholderType,
                )
            },
        )

        content?.let { slot ->
            slot()
        }
    }
}
