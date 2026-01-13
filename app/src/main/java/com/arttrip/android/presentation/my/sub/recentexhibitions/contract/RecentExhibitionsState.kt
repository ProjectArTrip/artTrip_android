package com.arttrip.android.presentation.my.sub.recentexhibitions.contract

import com.arttrip.android.domain.model.review.MyReview

data class RecentExhibitionsState(
    val isLoading: Boolean = false,
    val reviews: List<MyReview> = dummyMyReviews(),
    val isRemoveDialogVisible: Boolean = false,
) {
    val isEmpty: Boolean
        get() = !isLoading && reviews.isEmpty()
}

private fun dummyMyReviews(): List<MyReview> =
    listOf(
        MyReview(
            id = 1,
            exhibitionId = 101,
            exhibitionTitle = "모네와 인상주의",
            visitedDate = "2025.11.30",
            content = "색감이 너무 좋아서 한참을 서 있었어요. 오디오가이드 추천!",
            thumbnailUrl = "https://images.unsplash.com/photo-1520697830682-bbb6e85e2b0d?auto=format&fit=crop&w=600&q=80",
        ),
        MyReview(
            id = 2,
            exhibitionId = 205,
            exhibitionTitle = "미디어아트: 빛의 정원",
            visitedDate = "2025.12.21",
            content = "사진은 예쁘게 나오는데 사람 많으면 동선이 좀 답답했어요.",
            thumbnailUrl = "https://picsum.photos/100/100",
        ),
        MyReview(
            id = 3,
            exhibitionId = 309,
            exhibitionTitle = "한국 현대 조각전",
            visitedDate = "2025.11.30",
            content = "작품 설명이 좋아서 이해하기 쉬웠고, 전시 구성도 탄탄했어요.",
            thumbnailUrl = "https://picsum.photos/100/100",
        ),
        MyReview(
            id = 4,
            exhibitionId = 412,
            exhibitionTitle = "피카소, 끝없는 변주",
            visitedDate = "2026.01.03",
            content = "전시 동선이 깔끔해서 보기 편했고, 초반/후반 테마가 잘 나뉘어 있어서 집중하기 좋았어요.",
            thumbnailUrl = "https://picsum.photos/100/100",
        ),
        MyReview(
            id = 5,
            exhibitionId = 527,
            exhibitionTitle = "도시와 건축 사진전",
            visitedDate = "2025.12.14",
            content = "사진마다 설명이 자세해서 좋았고, 조명이 어두운 편이라 작품 감상이 더 몰입됐어요.",
            thumbnailUrl = "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=600&q=80",
        ),
        MyReview(
            id = 6,
            exhibitionId = 638,
            exhibitionTitle = "색채의 기록: 추상 회화전",
            visitedDate = "2026.01.07",
            content =
                "전체적으로 전시가 조용하고 편안해서 오래 머물기 좋았어요. " +
                    "특히 중간 섹션의 대형 캔버스들이 압도적이었고, 가까이서 보면 붓터치 레이어가 정말 촘촘하게 쌓여 있더라고요. " +
                    "다만 안내 표지판이 조금 작아서 초반에 동선을 헷갈렸고, 인기 작품 앞은 잠깐 대기가 있었어요. " +
                    "마지막 방에서는 색 조합이 강렬한 작품들이 이어지는데, 사진으로는 다 안 담겨서 아쉬웠습니다. " +
                    "다음에는 평일 오전에 다시 와서 천천히 보고 싶어요.",
            thumbnailUrl = "https://images.unsplash.com/photo-1519681393784-d120267933ba?auto=format&fit=crop&w=600&q=80",
        ),
    )
