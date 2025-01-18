package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.InsightRemoteDataSource
import info.imdang.data.model.response.common.PagingResponse
import info.imdang.data.model.response.insight.InsightIdResponse
import info.imdang.data.model.response.insight.InsightResponse
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
}
