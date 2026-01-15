package com.arttrip.android.presentation.my.sub.settings.sub.notice.contract

data class NoticeState(
    val notices: List<NoticeUiModel> = dummyNotices,
)

data class NoticeUiModel(
    val id: Int,
    val title: String,
    val date: String, // "2025-10-25"
    val content: String,
)

val dummyNotices: List<NoticeUiModel> =
    listOf(
        NoticeUiModel(
            id = 1,
            title = "아트트립 업데이트 안내 (1.0.0)",
            date = "2025-10-25",
            content =
                """
                더 나은 전시 탐색 경험을 위해 홈 화면과 전시 상세 화면의 사용성을 개선했어요.

                - 전시 카드 정보 배치 개선
                - 상세 화면 스크롤 UX 개선
                - 이미지 로딩 안정화

                앱이 최신 버전인지 확인해주세요.
                """.trimIndent(),
        ),
        NoticeUiModel(
            id = 2,
            title = "스탬프 기능 오픈",
            date = "2025-11-02",
            content =
                """
                전시를 방문하면 스탬프를 모을 수 있어요.

                스탬프는 마이페이지에서 확인할 수 있으며,
                일부 전시는 스탬프 발급 조건이 다를 수 있습니다.

                [안내]
                - 전시별 발급 시간/위치 기준이 다를 수 있어요.
                - 현장 네트워크 환경에 따라 발급이 지연될 수 있어요.
                """.trimIndent(),
        ),
        NoticeUiModel(
            id = 3,
            title = "약관 개정 사전 안내",
            date = "2026-01-05",
            content =
                """
                서비스 이용약관 및 개인정보 처리방침이 일부 개정될 예정입니다.
                주요 변경 사항과 적용 일자는 공지사항을 통해 다시 안내드릴게요.

                - 적용 예정일: 2026-01-20
                - 주요 변경 내용
                  1) 서비스 제공 범위 및 고지 문구 일부 수정
                  2) 이벤트/마케팅 알림 수신 동의 안내 문구 정리
                  3) 일부 용어 정의 및 표현 통일

                [유의사항]
                - 개정된 약관은 적용일 이후 서비스 이용 시 효력이 발생합니다.
                - 문의 사항은 고객센터 또는 앱 내 문의하기를 통해 남겨주세요.

                감사합니다.
                """.trimIndent(),
        ),
    )
