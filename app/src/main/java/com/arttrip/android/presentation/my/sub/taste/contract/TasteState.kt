package com.arttrip.android.presentation.my.sub.taste.contract

import com.arttrip.android.domain.model.usertaste.Taste

data class TasteState(
    val genres: List<Taste> = dummyGenres,
    val styles: List<Taste> = dummyStyles,
    val selectedGenreIds: Set<Int> = emptySet(),
    val selectedStyleIds: Set<Int> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
) {
    val isNextEnabled: Boolean
        get() = selectedGenreIds.isNotEmpty()
}

private val dummyGenres: List<Taste> =
    listOf(
        Taste(id = 1, name = "회화"),
        Taste(id = 2, name = "사진"),
        Taste(id = 3, name = "조각"),
        Taste(id = 4, name = "미디어아트"),
        Taste(id = 5, name = "설치미술"),
        Taste(id = 6, name = "공예"),
        Taste(id = 7, name = "디자인"),
        Taste(id = 8, name = "일러스트"),
        Taste(id = 9, name = "서예"),
        Taste(id = 10, name = "건축"),
    )

private val dummyStyles: List<Taste> =
    listOf(
        Taste(id = 101, name = "감성적인"),
        Taste(id = 102, name = "몰입형"),
        Taste(id = 103, name = "체험형"),
        Taste(id = 104, name = "미니멀"),
        Taste(id = 105, name = "레트로"),
        Taste(id = 106, name = "힙한"),
        Taste(id = 107, name = "가족/키즈"),
        Taste(id = 108, name = "데이트"),
        Taste(id = 109, name = "조용한"),
        Taste(id = 110, name = "화려한"),
    )
