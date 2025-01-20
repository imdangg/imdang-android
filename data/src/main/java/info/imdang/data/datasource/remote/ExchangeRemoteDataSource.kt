package info.imdang.data.datasource.remote

import info.imdang.data.model.exchange.ExchangeResponse
import info.imdang.data.model.request.exchange.RequestExchangeRequest

interface ExchangeRemoteDataSource {

    suspend fun requestExchange(
        requestExchangeRequest: RequestExchangeRequest
    ): ExchangeResponse
}
