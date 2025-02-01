package info.imdang.data.datasource.remote

import info.imdang.data.model.exchange.ExchangeResponse
import info.imdang.data.model.request.exchange.RequestExchangeRequest
import info.imdang.data.model.request.exchange.ResponseExchangeRequest

interface ExchangeRemoteDataSource {

    suspend fun requestExchange(
        requestExchangeRequest: RequestExchangeRequest
    ): ExchangeResponse

    suspend fun acceptExchange(
        responseExchangeRequest: ResponseExchangeRequest
    ): ExchangeResponse

    suspend fun rejectExchange(
        responseExchangeRequest: ResponseExchangeRequest
    ): ExchangeResponse
}
