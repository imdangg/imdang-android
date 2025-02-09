package info.imdang.data.datasource.remote

import info.imdang.data.model.request.insight.RecommendInsightRequest
import info.imdang.data.model.request.insight.ReportInsightRequest
import info.imdang.data.model.response.common.PagingResponse
import info.imdang.data.model.response.insight.InsightDetailResponse
import info.imdang.data.model.response.insight.InsightIdResponse
import info.imdang.data.model.response.insight.InsightResponse
import info.imdang.data.model.response.insight.VisitAptComplexResponse
import info.imdang.domain.model.insight.InsightDto
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface InsightRemoteDataSource {

    suspend fun writeInsight(
        createInsightCommand: RequestBody,
        mainImage: MultipartBody.Part
    ): InsightIdResponse

    suspend fun updateInsight(
        updateInsightCommand: RequestBody,
        mainImage: MultipartBody.Part?
    ): InsightIdResponse

    suspend fun getInsights(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto>

    suspend fun getInsightsByAptComplex(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        aptComplex: String
    ): PagingResponse<InsightResponse, InsightDto>

    suspend fun getInsightsByAddress(
        siGunGu: String,
        eupMyeonDong: String,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto>

    suspend fun getInsightsByDate(
        date: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto>

    suspend fun getInsightDetail(insightId: String): InsightDetailResponse

    suspend fun recommendInsight(
        recommendInsightRequest: RecommendInsightRequest
    ): InsightIdResponse

    suspend fun reportInsight(
        reportInsightRequest: ReportInsightRequest
    ): InsightIdResponse

    suspend fun getVisitedAptComplexes(): List<VisitAptComplexResponse>
}
