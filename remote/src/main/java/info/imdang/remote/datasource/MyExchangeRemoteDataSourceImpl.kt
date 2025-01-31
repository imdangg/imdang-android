package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.MyExchangeRemoteDataSource
import info.imdang.data.model.response.common.PagingResponse
import info.imdang.data.model.response.insight.InsightResponse
import info.imdang.domain.model.insight.InsightDto
import info.imdang.remote.service.MyExchangeService
import javax.inject.Inject

internal class MyExchangeRemoteDataSourceImpl @Inject constructor(
    private val myExchangeService: MyExchangeService
) : MyExchangeRemoteDataSource {

    override suspend fun getRequestedMyExchanges(
        requestMemberId: String,
        exchangeRequestStatus: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto> = myExchangeService.getRequestedMyExchanges(
        requestMemberId = requestMemberId,
        exchangeRequestStatus = exchangeRequestStatus,
        page = page,
        size = size,
        direction = direction,
        properties = properties
    )

    override suspend fun getRequestedOthersExchanges(
        requestedMemberId: String,
        exchangeRequestStatus: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto> = myExchangeService.getRequestedOthersExchanges(
        requestedMemberId = requestedMemberId,
        exchangeRequestStatus = exchangeRequestStatus,
        page = page,
        size = size,
        direction = direction,
        properties = properties
    )
}
