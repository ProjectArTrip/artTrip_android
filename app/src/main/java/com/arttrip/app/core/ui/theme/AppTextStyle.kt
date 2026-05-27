package com.arttrip.app.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em

object AppTextStyle {
    val Headline: TextStyle
        @Composable get() =
            with(LocalDensity.current) {
                TextStyle(
                    fontSize = 20.dp.toSp(),
                    lineHeight = 28.dp.toSp(),
                    fontFamily = Pretendard,
                    fontWeight = FontWeight(700),
                    letterSpacing = (-0.02).em,
                )
            }

    val Title01Bold: TextStyle
        @Composable get() =
            with(LocalDensity.current) {
                TextStyle(
                    fontSize = 18.dp.toSp(),
                    lineHeight = 20.dp.toSp(),
                    fontFamily = Pretendard,
                    fontWeight = FontWeight(700),
                    letterSpacing = (-0.02).em,
                )
            }

    val Title01Light: TextStyle
        @Composable get() =
            with(LocalDensity.current) {
                TextStyle(
                    fontSize = 18.dp.toSp(),
                    lineHeight = 20.dp.toSp(),
                    fontFamily = Pretendard,
                    fontWeight = FontWeight(300),
                    letterSpacing = (-0.02).em,
                )
            }

    val Title02Bold: TextStyle
        @Composable get() =
            with(LocalDensity.current) {
                TextStyle(
                    fontSize = 16.dp.toSp(),
                    lineHeight = 18.dp.toSp(),
                    fontFamily = Pretendard,
                    fontWeight = FontWeight(700),
                    letterSpacing = (-0.02).em,
                )
            }

    val Title02Light: TextStyle
        @Composable get() =
            with(LocalDensity.current) {
                TextStyle(
                    fontSize = 16.dp.toSp(),
                    lineHeight = 18.dp.toSp(),
                    fontFamily = Pretendard,
                    fontWeight = FontWeight(300),
                    letterSpacing = (-0.02).em,
                )
            }

    val Body01Bold: TextStyle
        @Composable get() =
            with(LocalDensity.current) {
                TextStyle(
                    fontSize = 14.dp.toSp(),
                    lineHeight = 16.dp.toSp(),
                    fontFamily = Pretendard,
                    fontWeight = FontWeight(700),
                    letterSpacing = (-0.02).em,
                )
            }

    val Body01Regular: TextStyle
        @Composable get() =
            with(LocalDensity.current) {
                TextStyle(
                    fontSize = 14.dp.toSp(),
                    lineHeight = 20.dp.toSp(),
                    fontFamily = Pretendard,
                    fontWeight = FontWeight(400),
                    letterSpacing = (-0.02).em,
                )
            }

    val Body01Light: TextStyle
        @Composable get() =
            with(LocalDensity.current) {
                TextStyle(
                    fontSize = 14.dp.toSp(),
                    lineHeight = 16.dp.toSp(),
                    fontFamily = Pretendard,
                    fontWeight = FontWeight(300),
                    letterSpacing = (-0.02).em,
                )
            }

    val Body02Bold: TextStyle
        @Composable get() =
            with(LocalDensity.current) {
                TextStyle(
                    fontSize = 12.dp.toSp(),
                    fontFamily = Pretendard,
                    fontWeight = FontWeight(700),
                )
            }

    val Body02Regular: TextStyle
        @Composable get() =
            with(LocalDensity.current) {
                TextStyle(
                    fontSize = 12.dp.toSp(),
                    fontFamily = Pretendard,
                    fontWeight = FontWeight(400),
                )
            }

    val Body02Light: TextStyle
        @Composable get() =
            with(LocalDensity.current) {
                TextStyle(
                    fontSize = 12.dp.toSp(),
                    fontFamily = Pretendard,
                    fontWeight = FontWeight(300),
                )
            }

    val Body03Regular: TextStyle
        @Composable get() =
            with(LocalDensity.current) {
                TextStyle(
                    fontSize = 11.dp.toSp(),
                    fontFamily = Pretendard,
                    fontWeight = FontWeight(400),
                )
            }
}
