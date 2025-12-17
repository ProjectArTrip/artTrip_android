package com.arttrip.android.data.remote.model.home

import kotlinx.serialization.Serializable

sealed interface RecommendExhibitListRequestDto {
    val isDomestic: Boolean
}

data class ForeignExhibitListRequestDto(
    override val isDomestic: Boolean = false,
    val country: String,
) : RecommendExhibitListRequestDto

data class DomesticExhibitListRequestDto(
    override val isDomestic: Boolean = true,
    val region: String,
) : RecommendExhibitListRequestDto