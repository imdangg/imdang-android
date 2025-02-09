package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.InsightRemoteDataSource
import info.imdang.data.model.request.insight.RecommendInsightRequest
import info.imdang.data.model.request.insight.ReportInsightRequest
import info.imdang.data.model.response.common.PagingResponse
import info.imdang.data.model.response.insight.InsightDetailResponse
import info.imdang.data.model.response.insight.InsightIdResponse
import info.imdang.data.model.response.insight.InsightResponse
import info.imdang.data.model.response.insight.VisitAptComplexResponse
import info.imdang.domain.model.insight.InsightDto
import info.imdang.remote.service.InsightService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

internal class InsightRemoteDataSourceImpl @Inject constructor(
    private val insightService: InsightService
) : InsightRemoteDataSource {

    override suspend fun writeInsight(
        createInsightCommand: RequestBody,
        mainImage: MultipartBody.Part
    ): InsightIdResponse = insightService.writeInsight(
        createInsightCommand = createInsightCommand,
        mainImage = mainImage
    )

    override suspend fun updateInsight(
        updateInsightCommand: RequestBody,
        mainImage: MultipartBody.Part?
    ): InsightIdResponse = insightService.updateInsight(
        updateInsightCommand = updateInsightCommand,
        mainImage = mainImage
    )

    override suspend fun getInsights(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto> = insightService.getInsights(
        page = page,
        size = size,
        direction = direction,
        properties = properties
    )

    override suspend fun getInsightsByAptComplex(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        aptComplex: String
    ): PagingResponse<InsightResponse, InsightDto> = insightService.getInsightsByAptComplex(
        page = page,
        size = size,
        direction = direction,
        properties = properties,
        aptComplex = aptComplex
    )

    override suspend fun getInsightsByAddress(
        siGunGu: String,
        eupMyeonDong: String,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto> = insightService.getInsightsByAddress(
        siGunGu = siGunGu,
        eupMyeonDong = eupMyeonDong,
        page = page,
        size = size,
        direction = direction,
        properties = properties
    )

    override suspend fun getInsightsByDate(
        date: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto> = insightService.getInsightsByDate(
        date = date,
        page = page,
        size = size,
        direction = direction,
        properties = properties
    )

    override suspend fun getInsightDetail(insightId: String): InsightDetailResponse =
        insightService.getInsightDetail(insightId)

    override suspend fun recommendInsight(
        recommendInsightRequest: RecommendInsightRequest
    ): InsightIdResponse = insightService.recommendInsight(recommendInsightRequest)

    override suspend fun reportInsight(
        reportInsightRequest: ReportInsightRequest
    ): InsightIdResponse = insightService.reportInsight(reportInsightRequest)

    override suspend fun getVisitedAptComplexes(): List<VisitAptComplexResponse> =
        insightService.getVisitedAptComplexes()
}
