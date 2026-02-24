package com.arttrip.android.presentation.home.ui.datefilter

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.core.ui.component.button.AppFilterChip
import com.arttrip.android.core.ui.component.button.AppFilterChipCase
import com.arttrip.android.core.ui.component.sheet.AppBottomSheetTopBar
import com.arttrip.android.core.ui.component.sheet.AppModalBottomSheet
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle

enum class FilterMenu { Country, Date }

@Composable
fun DateFilterBottomSheet(
    visible: Boolean,
    onDismissRequest: () -> Unit,
) {
    var expandedMenu by rememberSaveable { mutableStateOf<FilterMenu?>(null) }

    var selectedCountry by rememberSaveable { mutableStateOf<ForeignCountry?>(ForeignCountry.Entire) }

    var dateDesc by rememberSaveable { mutableStateOf<String?>(null) }

    fun toggleMenu(menu: FilterMenu) {
        expandedMenu = if (expandedMenu == menu) null else menu
    }

    fun closeMenu() {
        expandedMenu = null
    }

    AppModalBottomSheet(
        visible = visible,
        onDismissRequest = onDismissRequest,
        topBar = AppBottomSheetTopBar.DragHandle,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(18.dp))
            Text(
                text = "국가 및 날짜 선택",
                style = AppTextStyle.Title02Bold,
                color = AppColor.TextPrimary,
            )
            Spacer(Modifier.height(24.dp))

            FilterMenuItem(
                title = "국가 선택",
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
                        closeMenu()
                    },
                )
            }

            Spacer(Modifier.height(12.dp))

            FilterMenuItem(
                title = "날짜 선택",
                description = dateDesc,
                iconResId = R.drawable.ic_calendar_24,
                expanded = expandedMenu == FilterMenu.Date,
                onHeaderClick = {
                    toggleMenu(FilterMenu.Date)
                },
            ) {
                DateFilterContent(
                    onPickPreset = { preset ->
                        dateDesc = preset
                        closeMenu()
                    },
                )
            }
        }
    }
}

@Composable
private fun FilterMenuItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    @DrawableRes iconResId: Int,
    expanded: Boolean,
    onHeaderClick: () -> Unit,
    expandedContent: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(8.dp)
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 8.dp,
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
                ).padding(top = 16.dp, bottom = 15.dp),
    ) {
        FilterMenuHeader(
            title = title,
            description = description,
            iconResId = iconResId,
            onClick = onHeaderClick,
        )
        if (expanded) {
            HorizontalDivider(
                modifier.padding(horizontal = 12.dp),
                thickness = 1.dp,
                color = AppColor.Gray50,
            )
        } else {
            Spacer(Modifier.height(1.dp))
        }

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(tween(220)) + fadeIn(tween(220)),
            exit = shrinkVertically(tween(180)) + fadeOut(tween(180)),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                content = expandedContent,
            )
        }
    }
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
                .clickable(onClick = onClick)
                .padding(start = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
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
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
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

@Composable
private fun DateFilterContent(onPickPreset: (String) -> Unit) {
    // 커스텀 데이트피커 or 버튼들 등
}
