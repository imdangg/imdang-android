package info.imdang.domain.repository

import info.imdang.domain.model.exchange.ExchangeDto

interface ExchangeRepository {

    suspend fun requestExchange(
        insightId: String,
        memberId: String?,
        myInsightId: String?,
        couponId: String?
    ): ExchangeDto
}
