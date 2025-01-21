package info.imdang.remote.service

import info.imdang.data.model.exchange.ExchangeResponse
import info.imdang.data.model.request.exchange.RequestExchangeRequest
import retrofit2.http.Body
import retrofit2.http.POST

internal interface ExchangeService {

    /** 인사이트 교환 요청 */
    @POST("exchanges/request")
    suspend fun requestExchange(
        @Body requestExchangeRequest: RequestExchangeRequest
    ): ExchangeResponse
}
