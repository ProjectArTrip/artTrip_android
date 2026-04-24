package com.arttrip.app.presentation.home.ui.feedback

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arttrip.app.R
import com.arttrip.app.core.ui.component.skeleton.StaticSkeleton

@Composable
fun RecommendSectionLoading() {
    Row(
        modifier =
            Modifier
                .padding(start = 24.dp)
                .horizontalScroll(rememberScrollState(), enabled = false),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        repeat(5) {
            RecommendExhibitionSkeleton()
        }
    }
}

@Composable
fun PersonalizedSectionLoading() {
    Row(
        modifier =
            Modifier
                .horizontalScroll(rememberScrollState(), enabled = false),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        repeat(5) {
            PersonalizedExhibitionSkeleton()
        }
    }
}

@Composable
fun ScheduleSectionLoading() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        repeat(2) {
            ScheduleAndGenreExhibitionSkeleton()
        }
    }
}

@Composable
fun CurationSectionLoading() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TitleSkeleton()
            Icon(
                painter = painterResource(R.drawable.ic_more_24),
                contentDescription = null,
            )
        }
        Spacer(
            modifier =
                Modifier
                    .height(4.dp),
        )
        Row {
            Spacer(
                modifier =
                    Modifier
                        .width(24.dp),
            )
            SubTitleSkeleton()
        }

        Spacer(
            modifier =
                Modifier
                    .height(12.dp),
        )
        Row(
            modifier =
                Modifier
                    .horizontalScroll(rememberScrollState(), enabled = false)
                    .padding(start = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            repeat(7) {
                RecommendExhibitionSkeleton()
            }
        }
    }
}

@Composable
fun GenreSectionLoading() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        repeat(3) {
            ScheduleAndGenreExhibitionSkeleton()
        }
    }
}

@Composable
fun TitleSkeleton() {
    StaticSkeleton(
        modifier =
            Modifier
                .width(160.dp)
                .height(20.dp),
        shape = RoundedCornerShape(100.dp),
    )
}

@Composable
fun SubTitleSkeleton() {
    StaticSkeleton(
        modifier =
            Modifier
                .width(240.dp)
                .height(20.dp),
        shape = RoundedCornerShape(100.dp),
    )
}

@Composable
fun DateSkeleton() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        StaticSkeleton(
            modifier = Modifier.size(28.dp),
            shape = CircleShape,
        )
        Spacer(
            modifier =
                Modifier
                    .height(4.dp),
        )
        StaticSkeleton(
            modifier = Modifier.size(16.dp),
            shape = CircleShape,
        )
    }
}

@Composable
fun ChipSkeleton() {
    StaticSkeleton(
        modifier =
            Modifier
                .width(76.dp)
                .height(32.dp),
        shape = RoundedCornerShape(100.dp),
    )
}

@Composable
fun RecommendExhibitionSkeleton() {
    StaticSkeleton(
        modifier =
            Modifier
                .width(180.dp)
                .height(240.dp),
        shape = RoundedCornerShape(8.dp),
    )
}

@Composable
fun PersonalizedExhibitionSkeleton() {
    Column(
        modifier =
            Modifier
                .width(120.dp),
    ) {
        StaticSkeleton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(150.dp),
            shape = RoundedCornerShape(8.dp),
        )
        Spacer(
            modifier =
                Modifier
                    .height(8.dp),
        )
        StaticSkeleton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(14.dp),
            shape = RoundedCornerShape(8.dp),
        )
        Spacer(
            modifier =
                Modifier
                    .height(4.dp),
        )
        StaticSkeleton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(14.dp),
            shape = RoundedCornerShape(8.dp),
        )
    }
}

@Composable
fun ScheduleAndGenreExhibitionSkeleton() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StaticSkeleton(
            modifier =
                Modifier
                    .width(100.dp)
                    .height(100.dp),
            shape = RoundedCornerShape(8.dp),
        )
        Spacer(
            modifier =
                Modifier
                    .width(12.dp),
        )
        Column {
            StaticSkeleton(
                modifier =
                    Modifier
                        .width(64.dp)
                        .height(16.dp),
                shape = RoundedCornerShape(10.dp),
            )
            Spacer(
                modifier =
                    Modifier
                        .height(4.dp),
            )
            StaticSkeleton(
                modifier =
                    Modifier
                        .width(160.dp)
                        .height(16.dp),
                shape = RoundedCornerShape(10.dp),
            )
            Spacer(
                modifier =
                    Modifier
                        .height(4.dp),
            )
            StaticSkeleton(
                modifier =
                    Modifier
                        .width(120.dp)
                        .height(14.dp),
                shape = RoundedCornerShape(10.dp),
            )
            Spacer(
                modifier =
                    Modifier
                        .height(2.dp),
            )
            StaticSkeleton(
                modifier =
                    Modifier
                        .width(120.dp)
                        .height(14.dp),
                shape = RoundedCornerShape(10.dp),
            )
        }
    }
}

@Composable
fun LoadingExhibitionList() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
    ) {
        repeat(5) {
            Spacer(
                modifier =
                    Modifier
                        .height(12.dp),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                StaticSkeleton(
                    modifier =
                        Modifier
                            .width(100.dp)
                            .height(100.dp),
                    shape = RoundedCornerShape(8.dp),
                )
                Spacer(
                    modifier =
                        Modifier
                            .width(12.dp),
                )
                Column {
                    StaticSkeleton(
                        modifier =
                            Modifier
                                .width(160.dp)
                                .height(16.dp),
                        shape = RoundedCornerShape(10.dp),
                    )
                    Spacer(
                        modifier =
                            Modifier
                                .height(4.dp),
                    )
                    StaticSkeleton(
                        modifier =
                            Modifier
                                .width(120.dp)
                                .height(14.dp),
                        shape = RoundedCornerShape(10.dp),
                    )
                    Spacer(
                        modifier =
                            Modifier
                                .height(2.dp),
                    )
                    StaticSkeleton(
                        modifier =
                            Modifier
                                .width(120.dp)
                                .height(14.dp),
                        shape = RoundedCornerShape(10.dp),
                    )
                }
            }
        }
    }
}
