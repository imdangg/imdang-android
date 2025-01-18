package info.imdang.domain.model.common

data class PagingDto<T>(
    val totalElements: Int,
    val totalPages: Int,
    val size: Int,
    val number: Int,
    val sort: SortDto,
    val numberOfElements: Int,
    val pageable: PageableDto,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean,
    val content: List<T>
)

data class SortDto(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)

data class PageableDto(
    val offset: Int,
    val sort: SortDto,
    val paged: Boolean,
    val pageNumber: Int,
    val pageSize: Int,
    val unpaged: Boolean
)
