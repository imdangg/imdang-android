package info.imdang.remote.service

import info.imdang.data.model.exchange.ExchangeResponse
import info.imdang.data.model.request.exchange.RequestExchangeRequest
import info.imdang.data.model.request.exchange.ResponseExchangeRequest
import retrofit2.http.Body
import retrofit2.http.POST

internal interface ExchangeService {

    /** 인사이트 교환 요청 */
    @POST("exchanges/request")
    suspend fun requestExchange(
        @Body requestExchangeRequest: RequestExchangeRequest
    ): ExchangeResponse

    /** 인사이트 교환 수락 */
    @POST("exchanges/accept")
    suspend fun acceptExchange(
        @Body responseExchangeRequest: ResponseExchangeRequest
    ): ExchangeResponse

    /** 인사이트 교환 거절 */
    @POST("exchanges/reject")
    suspend fun rejectExchange(
        @Body responseExchangeRequest: ResponseExchangeRequest
    ): ExchangeResponse
}
