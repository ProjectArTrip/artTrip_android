package com.arttrip.app.presentation.home.sub.datefilterresult.contract

import com.arttrip.app.core.model.enums.domestic.DomesticRegion
import com.arttrip.app.core.model.enums.foreign.ForeignCountry

sealed interface ExhibitionLocation {
    val label: String
    val enumName: String

    data class Domestic(
        val region: DomesticRegion,
    ) : ExhibitionLocation {
        override val label get() = region.label
        override val enumName get() = region.name
    }

    data class Foreign(
        val country: ForeignCountry,
    ) : ExhibitionLocation {
        override val label get() = country.label
        override val enumName get() = country.name
    }
}
