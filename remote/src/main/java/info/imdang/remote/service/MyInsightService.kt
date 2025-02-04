package info.imdang.remote.service

import info.imdang.data.model.response.myinsight.AptComplexResponse
import info.imdang.data.model.response.common.PagingResponse
import info.imdang.data.model.response.insight.InsightResponse
import info.imdang.data.model.response.myinsight.MyInsightAddressResponse
import info.imdang.domain.model.insight.InsightDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

internal interface MyInsightService {

    /** 보관함 주소 목록 조회 */
    @GET("my-insights/districts")
    suspend fun getAddresses(): List<MyInsightAddressResponse>

    /** 보관함 단지 목록 조회 */
    @GET("my-insights/by-district/apartment-complexes")
    suspend fun getComplexesByAddress(
        @QueryMap queries: Map<String, String>
    ): List<AptComplexResponse>

    /** 보관함 인사이트 목록 조회 */
    @GET("my-insights")
    suspend fun getMyInsightsByAddress(
        @QueryMap addressQueries: Map<String, String>,
        @Query("apartmentComplexName") aptComplexName: String?,
        @Query("onlyMine") onlyMine: Boolean?,
        @Query("pageNumber") page: Int?,
        @Query("pageSize") size: Int?,
        @Query("direction") direction: String?,
        @Query("properties") properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto>

    /** 내 인사이트 목록 조회 */
    @GET("my-insights/created-by-me")
    suspend fun getMyInsights(
        @Query("pageNumber") page: Int?,
        @Query("pageSize") size: Int?,
        @Query("direction") direction: String?,
        @Query("properties") properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto>
}
