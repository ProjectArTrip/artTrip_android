package com.arttrip.android.presentation.home.sub.search.contract

data class SearchState(
    val recentKeywordList: List<String> =
        listOf(
            "현대미술",
            "사진",
            "무료",
            "예약필수",
            "가족추천",
            "야간개장",
            "굿즈",
            "전시해설",
            "DDP",
            "서울",
            "인기전시",
            "신규",
        ),
    val recommendKeywordList: List<String> =
        listOf(
            "국내",
            "사진",
            "무료",
            "서울",
            "인기전시",
            "신규",
        ),
)
