package com.arttrip.app.data.remote.mapper.curation

import com.arttrip.app.data.remote.mapper.home.toDomain
import com.arttrip.app.data.remote.model.curation.CurationResponse
import com.arttrip.app.domain.model.curation.Curation

fun CurationResponse.toDomain(): Curation =
    Curation(
        curationId = curationId,
        title = title,
        subtitle = subtitle,
        exhibits = exhibits.map { it.toDomain() },
    )