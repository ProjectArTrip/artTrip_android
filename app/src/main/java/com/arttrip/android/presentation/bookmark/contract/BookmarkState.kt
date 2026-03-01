package com.arttrip.android.presentation.bookmark.contract

import com.arttrip.android.core.model.enums.exhibition.ExhibitionStatus
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.presentation.bookmark.model.BookmarkLocationFilter

data class BookmarkState(
    val isLoading: Boolean = false,
    val sort: BookmarkSort = BookmarkSort.LATEST,
    val bookmarkList: List<Exhibition> =
        listOf(
            Exhibition(
                id = 1,
                title = "빛과 그림자의 정원",
                posterUrl = "https://picsum.photos/100/100",
                status = ExhibitionStatus.ONGOING,
                period = "2025.12.01 - 2026.02.28",
                hallName = "1전시실",
                country = "대한민국",
                region = "서울",
                isBookmarked = true,
            ),
            Exhibition(
                id = 2,
                title = "현대 미술의 흐름: 1990–2025",
                posterUrl = "https://picsum.photos/100/100",
                status = ExhibitionStatus.ENDING_SOON,
                period = "2025.11.15 - 2026.01.20",
                hallName = "기획전시관 A",
                country = "대한민국",
                region = "서울",
                isBookmarked = false,
            ),
            Exhibition(
                id = 3,
                title = "색채 실험실",
                posterUrl = "https://picsum.photos/100/100",
                status = ExhibitionStatus.UPCOMING,
                period = "2026.01.20 - 2026.03.10",
                hallName = "2전시실",
                country = "대한민국",
                region = "서울",
                isBookmarked = true,
            ),
            Exhibition(
                id = 4,
                title = "도시의 표정: 사진전",
                posterUrl = "https://picsum.photos/100/100",
                status = ExhibitionStatus.ONGOING,
                period = "2025.10.01 - 2026.01.31",
                hallName = "갤러리 3",
                country = "대한민국",
                region = "서울",
                isBookmarked = false,
            ),
            Exhibition(
                id = 5,
                title = "초현실의 방 — 긴 제목 테스트용 전시 타이틀입니다긴 제목 테스트용 전시 타이틀입니다긴 제목 테스트용 전시 타이틀입니다",
                posterUrl = "https://picsum.photos/100/100",
                status = ExhibitionStatus.UPCOMING,
                period = "2025.08.10 - 2025.12.31",
                hallName = "메인홀",
                country = "대한민국",
                region = "서울",
                isBookmarked = true,
            ),
            Exhibition(
                id = 6,
                title = "공예의 시간",
                posterUrl = "https://picsum.photos/100/100",
                status = ExhibitionStatus.UPCOMING,
                period = "2026.02.01 - 2026.04.30",
                hallName = "특별전시실",
                country = "대한민국",
                region = "서울",
                isBookmarked = false,
            ),
            Exhibition(
                id = 7,
                title = "미디어아트: 사운드와 빛",
                posterUrl = "https://picsum.photos/100/100",
                status = ExhibitionStatus.ONGOING,
                period = "2025.12.20 - 2026.02.15",
                hallName = "B1 전시공간",
                country = "대한민국",
                region = "서울",
                isBookmarked = true,
            ),
            Exhibition(
                id = 8,
                title = "한국 근대 회화전",
                posterUrl = "https://picsum.photos/100/100",
                status = ExhibitionStatus.ENDING_SOON,
                period = "2025.09.01 - 2025.11.30",
                hallName = "상설전시관",
                country = "대한민국",
                region = "서울",
                isBookmarked = false,
            ),
        ),
    val bookmarkedMap: Map<Int, Boolean> = emptyMap(),
    val isFilterSheetVisible: Boolean = false,
    val appliedLocationFilter: BookmarkLocationFilter = BookmarkLocationFilter(),
    val editingLocationFilter: BookmarkLocationFilter = BookmarkLocationFilter(),
) {
    val isSearchEnabled: Boolean
        get() =
            editingLocationFilter.foreignCountries.isNotEmpty() ||
                editingLocationFilter.domesticRegions.isNotEmpty()
    val isEmpty: Boolean
        get() = bookmarkList.isEmpty()
}

enum class BookmarkSort {
    LATEST,
    DEADLINE,
}
