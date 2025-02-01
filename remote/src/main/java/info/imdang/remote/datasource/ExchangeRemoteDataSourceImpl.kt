package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.ExchangeRemoteDataSource
import info.imdang.data.model.exchange.ExchangeResponse
import info.imdang.data.model.request.exchange.RequestExchangeRequest
import info.imdang.data.model.request.exchange.ResponseExchangeRequest
import info.imdang.remote.service.ExchangeService
import javax.inject.Inject

internal class ExchangeRemoteDataSourceImpl @Inject constructor(
    private val exchangeService: ExchangeService
) : ExchangeRemoteDataSource {

    override suspend fun requestExchange(
        requestExchangeRequest: RequestExchangeRequest
    ): ExchangeResponse = exchangeService.requestExchange(requestExchangeRequest)

    override suspend fun acceptExchange(
        responseExchangeRequest: ResponseExchangeRequest
    ): ExchangeResponse = exchangeService.acceptExchange(responseExchangeRequest)

    override suspend fun rejectExchange(
        responseExchangeRequest: ResponseExchangeRequest
    ): ExchangeResponse = exchangeService.rejectExchange(responseExchangeRequest)
}
