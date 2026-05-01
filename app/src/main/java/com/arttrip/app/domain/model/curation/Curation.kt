package com.arttrip.app.domain.model.curation

import com.arttrip.app.domain.model.exhibition.Exhibition

data class Curation(
    val curationId: Long,
    val title: String,
    val subtitle: String,
    val exhibits: List<Exhibition>,
)
