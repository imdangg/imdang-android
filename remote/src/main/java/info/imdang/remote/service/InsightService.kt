package info.imdang.remote.service

import info.imdang.data.model.response.common.PagingResponse
import info.imdang.data.model.response.insight.InsightIdResponse
import info.imdang.data.model.response.insight.InsightResponse
import info.imdang.domain.model.insight.InsightDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

internal interface InsightService {

    /** 인사이트 작성 */
    @Multipart
    @POST("insights/create")
    suspend fun writeInsight(
        @Part("createInsightCommand") createInsightCommand: RequestBody,
        @Part mainImage: MultipartBody.Part
    ): InsightIdResponse

    /** 오늘 새롭게 올라온 인사이트 목록 조회 */
    @GET("insights")
    suspend fun getInsights(
        @Query("pageNumber") page: Int?,
        @Query("pageSize") size: Int?,
        @Query("direction") direction: String?,
        @Query("properties") properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto>

    /** 아파트 단지별 인사이트 목록 조회 */
    @GET("insights/by-apartment-complex")
    suspend fun getInsightsByAptComplex(
        @Query("pageNumber") page: Int?,
        @Query("pageSize") size: Int?,
        @Query("direction") direction: String?,
        @Query("properties") properties: List<String>?,
        @Query("apartmentComplexName") aptComplex: String
    ): PagingResponse<InsightResponse, InsightDto>
}
