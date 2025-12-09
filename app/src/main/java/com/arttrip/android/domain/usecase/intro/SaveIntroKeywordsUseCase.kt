package com.arttrip.android.domain.usecase.intro

import javax.inject.Inject

class SaveIntroKeywordsUseCase
    @Inject
    constructor(
        // private val userRepository: UserRepository,
    ) {
        /**
         * @param genreIds  선택된 전시 장르 ID 리스트
         * @param styleIds  선택된 전시 스타일 ID 리스트
         */
        suspend operator fun invoke(
            genreIds: Set<Int>,
            styleIds: Set<Int>,
        ) {
            // TODO: API 붙이기
        }
    }
