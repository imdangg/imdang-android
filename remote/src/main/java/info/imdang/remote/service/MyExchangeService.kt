package info.imdang.remote.service

import info.imdang.data.model.response.common.PagingResponse
import info.imdang.data.model.response.insight.InsightResponse
import info.imdang.domain.model.insight.InsightDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MyExchangeService {

    /** 내가 요청한 교환 목록 조회 */
    @GET("my-exchanges/requested-by-me")
    suspend fun getRequestedMyExchanges(
        @Query("requestMemberId") requestMemberId: String,
        @Query("exchangeRequestStatus") exchangeRequestStatus: String?,
        @Query("pageNumber") page: Int?,
        @Query("pageSize") size: Int?,
        @Query("direction") direction: String?,
        @Query("properties") properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto>

    /** 다른 사람이 요청한 인사이트 교환 목록 API */
    @GET("my-exchanges/requested-by-others")
    suspend fun getRequestedOthersExchanges(
        @Query("requestedMemberId") requestedMemberId: String,
        @Query("exchangeRequestStatus") exchangeRequestStatus: String?,
        @Query("pageNumber") page: Int?,
        @Query("pageSize") size: Int?,
        @Query("direction") direction: String?,
        @Query("properties") properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto>
}
