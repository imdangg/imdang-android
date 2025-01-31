package info.imdang.data.repository

import info.imdang.data.datasource.remote.MyExchangeRemoteDataSource
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.repository.MyExchangeRepository
import javax.inject.Inject

internal class MyExchangeRepositoryImpl @Inject constructor(
    private val myExchangeRemoteDataSource: MyExchangeRemoteDataSource
) : MyExchangeRepository {

    override suspend fun getRequestedMyExchanges(
        requestMemberId: String,
        exchangeRequestStatus: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingDto<InsightDto> = myExchangeRemoteDataSource.getRequestedMyExchanges(
        requestMemberId = requestMemberId,
        exchangeRequestStatus = exchangeRequestStatus,
        page = page,
        size = size,
        direction = direction,
        properties = properties
    ).mapper()

    override suspend fun getRequestedOthersExchanges(
        requestedMemberId: String,
        exchangeRequestStatus: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingDto<InsightDto> = myExchangeRemoteDataSource.getRequestedOthersExchanges(
        requestedMemberId = requestedMemberId,
        exchangeRequestStatus = exchangeRequestStatus,
        page = page,
        size = size,
        direction = direction,
        properties = properties
    ).mapper()
}
