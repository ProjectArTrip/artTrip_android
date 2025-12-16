sealed interface ExhibitListQueryModel {
    val singleGenre: String?
    val genres: List<String>
    val styles: List<String>
    val date: String
    val limit: Int
}

data class ForeignExhibitListQueryModel(
    val country: String,
    override val singleGenre: String? = null,
    override val genres: List<String> = emptyList(),
    override val styles: List<String> = emptyList(),
    override val date: String,
    override val limit: Int = 0,
) : ExhibitListQueryModel

data class DomesticExhibitListQueryModel(
    val region: String,
    override val singleGenre: String? = null,
    override val genres: List<String> = emptyList(),
    override val styles: List<String> = emptyList(),
    override val date: String,
    override val limit: Int = 0,
) : ExhibitListQueryModel