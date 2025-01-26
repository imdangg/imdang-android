package info.imdang.domain.repository

import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.insight.InsightDto

interface MyExchangeRepository {

    suspend fun getMyExchanges(
        requestMemberId: String,
        exchangeRequestStatus: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingDto<InsightDto>
}
