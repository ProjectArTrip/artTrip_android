package com.arttrip.android.core.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

object AppTextStyle {
    val Headline =
        TextStyle(
            fontSize = 20.sp,
            lineHeight = 28.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight(700),
            letterSpacing = (-2f / 20f).em,
        )

    val Title01Bold =
        TextStyle(
            fontSize = 18.sp,
            lineHeight = 20.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight(700),
            letterSpacing = (-2f / 18f).em,
        )

    val Title01Light =
        TextStyle(
            fontSize = 18.sp,
            lineHeight = 20.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight(300),
            letterSpacing = (-2f / 18f).em,
        )

    val Title02Bold =
        TextStyle(
            fontSize = 16.sp,
            lineHeight = 18.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight(700),
            letterSpacing = (-2f / 16f).em,
        )

    val Title02Light =
        TextStyle(
            fontSize = 16.sp,
            lineHeight = 18.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight(300),
            letterSpacing = (-2f / 16f).em,
        )

    val Body01Bold =
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 16.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight(700),
            letterSpacing = (-2f / 14f).em,
        )

    val Body01Regular =
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight(400),
            letterSpacing = (-2f / 14f).em,
        )

    val Body01Light =
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 16.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight(300),
            letterSpacing = (-2f / 14f).em,
        )

    val Body02Bold =
        TextStyle(
            fontSize = 12.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight(700),
        )

    val Body02Regular =
        TextStyle(
            fontSize = 12.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight(400),
        )

    val Body02Light =
        TextStyle(
            fontSize = 12.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight(300),
        )

    val Body03Regular =
        TextStyle(
            fontSize = 11.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight(400),
        )
}
