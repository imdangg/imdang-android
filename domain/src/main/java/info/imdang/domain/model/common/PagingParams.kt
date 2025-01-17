package info.imdang.domain.model.common

data class PagingParams<T>(
    val page: Int? = null,
    val size: Int? = null,
    val direction: String? = null,
    val properties: List<String>? = null,
    val additionalParams: T? = null
)
