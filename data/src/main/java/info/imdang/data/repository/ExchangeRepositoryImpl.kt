package info.imdang.data.repository

import info.imdang.data.datasource.remote.ExchangeRemoteDataSource
import info.imdang.data.model.request.exchange.RequestExchangeRequest
import info.imdang.data.model.request.exchange.ResponseExchangeRequest
import info.imdang.domain.model.exchange.ExchangeDto
import info.imdang.domain.repository.ExchangeRepository
import javax.inject.Inject

internal class ExchangeRepositoryImpl @Inject constructor(
    private val exchangeRemoteDataSource: ExchangeRemoteDataSource
) : ExchangeRepository {

    override suspend fun requestExchange(
        insightId: String,
        memberId: String?,
        myInsightId: String?,
        couponId: Long?
    ): ExchangeDto = exchangeRemoteDataSource.requestExchange(
        RequestExchangeRequest(
            requestedInsightId = insightId,
            requestMemberId = memberId,
            requestMemberInsightId = myInsightId,
            memberCouponId = couponId
        )
    ).mapper()

    override suspend fun acceptExchange(
        exchangeRequestId: String,
        memberId: String
    ): ExchangeDto = exchangeRemoteDataSource.acceptExchange(
        responseExchangeRequest = ResponseExchangeRequest(
            exchangeRequestId = exchangeRequestId,
            requestedMemberId = memberId
        )
    ).mapper()

    override suspend fun rejectExchange(
        exchangeRequestId: String,
        memberId: String
    ): ExchangeDto = exchangeRemoteDataSource.rejectExchange(
        responseExchangeRequest = ResponseExchangeRequest(
            exchangeRequestId = exchangeRequestId,
            requestedMemberId = memberId
        )
    ).mapper()
}
