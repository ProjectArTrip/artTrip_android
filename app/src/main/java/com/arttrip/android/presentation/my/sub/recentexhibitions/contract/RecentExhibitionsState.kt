package com.arttrip.android.presentation.my.sub.recentexhibitions.contract

data class RecentExhibitionsState(
    val isLoading: Boolean = false,
    val exhibitions: List<ExhibitionUiModel> = DUMMY_EXHIBITIONS,
)

data class ExhibitionUiModel(
    val id: Int,
    val title: String,
    val hallName: String,
    val url: String,
)

private val DUMMY_EXHIBITIONS =
    listOf(
        ExhibitionUiModel(
            id = 101,
            title = "빛의 정원",
            hallName = "서울시립미술관",
            url = "https://picsum.photos/100/100",
        ),
        ExhibitionUiModel(
            id = 102,
            title = "현대미술의 흐름",
            hallName = "국립현대미술관 서울",
            url = "https://picsum.photos/100/100",
        ),
        ExhibitionUiModel(
            id = 103,
            title = "색과 형태",
            hallName = "DDP 전시관",
            url = "https://picsum.photos/100/100",
        ),
        ExhibitionUiModel(
            id = 104,
            title = "사진, 도시를 기록하다사진, 도시를 기록하다사진, 도시를 기록하다사진, 도시를 기록하다사진, 도시를 기록하다사진, 도시를 기록하다",
            hallName = "예술의전당 한가람미술관",
            url = "https://picsum.photos/100/100",
        ),
        ExhibitionUiModel(
            id = 105,
            title = "시간의 조각",
            hallName = "성수 아트스페이스",
            url = "https://picsum.photos/100/100",
        ),
        ExhibitionUiModel(
            id = 106,
            title = "경계의 풍경",
            hallName = "아트선재센터",
            url = "https://picsum.photos/100/100",
        ),
        ExhibitionUiModel(
            id = 107,
            title = "오브제와 기억",
            hallName = "대림미술관",
            url = "https://picsum.photos/100/100",
        ),
        ExhibitionUiModel(
            id = 108,
            title = "추상의 언어",
            hallName = "리움미술관",
            url = "https://picsum.photos/100/100",
        ),
        ExhibitionUiModel(
            id = 109,
            title = "일상의 재해석",
            hallName = "부산현대미술관",
            url = "https://picsum.photos/100/100",
        ),
        ExhibitionUiModel(
            id = 110,
            title = "공간과 감각",
            hallName = "대구미술관",
            url = "https://picsum.photos/100/100",
        ),
    )
