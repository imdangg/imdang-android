package info.imdang.domain.model.common

data class PagingParams(
    val page: Int = 1,
    val size: Int? = null,
    val direction: String? = null,
    val properties: List<String>? = null,
    val totalCountListener: ((Int) -> Unit)? = null
)
