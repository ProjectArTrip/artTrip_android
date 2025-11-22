package com.arttrip.android.data.remote.mapper.exhibit

import com.arttrip.android.data.remote.model.exhibit.ExhibitInfoDto
import com.arttrip.android.domain.model.exhibit.ExhibitInfoModel

fun ExhibitInfoDto.toDomain(): ExhibitInfoModel =
    ExhibitInfoModel(
        id = id,
        name = name,
    )
