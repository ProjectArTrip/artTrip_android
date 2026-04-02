package com.arttrip.android.presentation.home.ui.datefilter

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.core.ui.component.button.AppButton
import com.arttrip.android.core.ui.component.button.AppButtonDefaults
import com.arttrip.android.core.ui.component.button.AppFilterChip
import com.arttrip.android.core.ui.component.button.AppFilterChipCase
import com.arttrip.android.core.ui.component.sheet.AppBottomSheetTopBar
import com.arttrip.android.core.ui.component.sheet.AppModalBottomSheet
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable

enum class FilterMenu { Country, Date }

@Composable
fun DateFilterBottomSheet(
    visible: Boolean,
    onDismissRequest: () -> Unit,
) {
    var expandedMenu by rememberSaveable { mutableStateOf<FilterMenu?>(null) }

    var selectedCountry by rememberSaveable { mutableStateOf<ForeignCountry?>(null) }

    var dateDesc by rememberSaveable { mutableStateOf<String?>(null) }

    val buttonBottomMargin = 16.dp
    val bottomContentPadding = 32.dp
    val bottomInset = AppButtonDefaults.Height + buttonBottomMargin

    fun toggleMenu(menu: FilterMenu) {
        expandedMenu = if (expandedMenu == menu) null else menu
    }

    val density = LocalDensity.current

    AppModalBottomSheet(
        visible = visible,
        onDismissRequest = onDismissRequest,
        topBar = AppBottomSheetTopBar.DragHandle,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(668.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.height(18.dp))
                Text(
                    text = "국가 및 날짜 선택",
                    style = AppTextStyle.Title02Bold,
                    color = AppColor.TextPrimary,
                )
                Spacer(Modifier.height(24.dp))

                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),
                ) {
                    CountryFilterMenuItem(
                        title = "국가",
                        description = selectedCountry?.label,
                        iconResId = R.drawable.ic_calendar_24,
                        expanded = expandedMenu == FilterMenu.Country,
                        onHeaderClick = {
                            toggleMenu(FilterMenu.Country)
                        },
                    ) {
                        CountryFilterChips(
                            selectedCountry = selectedCountry,
                            onCountryClick = { country ->
                                selectedCountry = country
                            },
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    DateFilterMenuItem(
                        title = "날짜",
                        description = dateDesc,
                        iconResId = R.drawable.ic_calendar_24,
                        expanded = expandedMenu == FilterMenu.Date,
                        onHeaderClick = {
                            toggleMenu(FilterMenu.Date)
                        },
                    ) {
                        DatePickerContent(
                            onPickPreset = { preset ->
                                dateDesc = preset
                            },
                        )
                    }
                }
                Spacer(Modifier.height(bottomContentPadding))
                Spacer(Modifier.height(bottomInset))
            }
            AppButton(
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(bottom = buttonBottomMargin),
                text = "적용하기",
                onClick = {},
            )
        }
    }
}

@Composable
private fun CountryFilterMenuItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    @DrawableRes iconResId: Int,
    expanded: Boolean,
    onHeaderClick: () -> Unit,
    expandedContent: @Composable ColumnScope.() -> Unit,
) {
    FilterMenuCard(
        modifier = modifier,
        expanded = expanded,
    ) {
        Spacer(Modifier.height(16.dp))

        FilterMenuHeader(
            title = title,
            description = description,
            iconResId = iconResId,
            onClick = onHeaderClick,
        )

        Spacer(Modifier.height(16.dp))

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut(),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    thickness = 1.dp,
                    color = AppColor.Gray50,
                )

                Spacer(Modifier.height(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    content = expandedContent,
                )

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun DateFilterMenuItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    @DrawableRes iconResId: Int,
    expanded: Boolean,
    onHeaderClick: () -> Unit,
    expandedContent: @Composable ColumnScope.() -> Unit,
) {
    FilterMenuCard(
        modifier = modifier,
        expanded = expanded,
    ) {
        Spacer(Modifier.height(16.dp))

        FilterMenuHeader(
            title = title,
            description = description,
            iconResId = iconResId,
            onClick = onHeaderClick,
        )

        Spacer(Modifier.height(16.dp))

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                content = expandedContent,
            )
        }
    }
}

@Composable
private fun FilterMenuCard(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(8.dp)

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = shape,
                    clip = false,
                ).background(
                    color = AppColor.Gray0,
                    shape = shape,
                ).then(
                    if (expanded) {
                        Modifier.border(
                            width = 1.dp,
                            color = AppColor.Gray50,
                            shape = shape,
                        )
                    } else {
                        Modifier
                    },
                ),
        content = content,
    )
}

@Composable
private fun FilterMenuHeader(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    @DrawableRes iconResId: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .noRippleClickable(onClick = onClick)
                .padding(start = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(iconResId),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Spacer(Modifier.width(12.dp))

        Text(
            text = title,
            style = AppTextStyle.Body01Bold,
            color = AppColor.TextPrimary,
        )

        if (description != null) {
            Spacer(Modifier.width(12.dp))
            Text(
                text = description,
                style = AppTextStyle.Body01Bold,
                color = AppColor.TextPoint,
            )
        }
    }
}

@Composable
private fun CountryFilterChips(
    selectedCountry: ForeignCountry?,
    onCountryClick: (ForeignCountry) -> Unit,
) {
    val countries = ForeignCountry.entries.toList()

    FlowRow(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        itemVerticalAlignment = Alignment.Top,
    ) {
        countries.forEach { country ->
            val selected = selectedCountry == country
            AppFilterChip(
                case = AppFilterChipCase.Case02,
                text = country.label,
                selected = selected,
                onClick = { onCountryClick(country) },
            )
        }
    }
}
