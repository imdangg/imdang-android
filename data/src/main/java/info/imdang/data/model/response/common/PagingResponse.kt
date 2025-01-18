package info.imdang.data.model.response.common

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.data.mapper.mapper
import info.imdang.domain.model.common.PageableDto
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.common.SortDto

data class PagingResponse<TR : DataToDomainMapper<T>, T>(
    val totalElements: Int,
    val totalPages: Int,
    val size: Int,
    val number: Int,
    val sort: SortResponse,
    val numberOfElements: Int,
    val pageable: PageableResponse,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean,
    val content: List<TR>
) : DataToDomainMapper<PagingDto<T>> {
    override fun mapper(): PagingDto<T> = PagingDto(
        totalElements = totalElements,
        totalPages = totalPages,
        size = size,
        number = number,
        sort = sort.mapper(),
        numberOfElements = numberOfElements,
        pageable = pageable.mapper(),
        first = first,
        last = last,
        empty = empty,
        content = content.mapper()
    )
}

data class SortResponse(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
) : DataToDomainMapper<SortDto> {
    override fun mapper(): SortDto = SortDto(
        empty = empty,
        sorted = sorted,
        unsorted = unsorted
    )
}

data class PageableResponse(
    val offset: Int,
    val sort: SortResponse,
    val paged: Boolean,
    val pageNumber: Int,
    val pageSize: Int,
    val unpaged: Boolean
) : DataToDomainMapper<PageableDto> {
    override fun mapper(): PageableDto = PageableDto(
        offset = offset,
        sort = sort.mapper(),
        paged = paged,
        pageNumber = pageNumber,
        pageSize = pageSize,
        unpaged = unpaged
    )
}
