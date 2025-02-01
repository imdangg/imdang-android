package info.imdang.domain.repository

import info.imdang.domain.model.exchange.ExchangeDto

interface ExchangeRepository {

    suspend fun requestExchange(
        insightId: String,
        memberId: String?,
        myInsightId: String?,
        couponId: Long?
    ): ExchangeDto

    suspend fun acceptExchange(
        exchangeRequestId: String,
        memberId: String
    ): ExchangeDto

    suspend fun rejectExchange(
        exchangeRequestId: String,
        memberId: String
    ): ExchangeDto
}
