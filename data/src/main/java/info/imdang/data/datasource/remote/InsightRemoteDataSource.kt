package info.imdang.data.datasource.remote

import info.imdang.data.model.response.common.PagingResponse
import info.imdang.data.model.response.insight.InsightIdResponse
import info.imdang.data.model.response.insight.InsightResponse
import info.imdang.domain.model.insight.InsightDto
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface InsightRemoteDataSource {

    suspend fun writeInsight(
        createInsightCommand: RequestBody,
        mainImage: MultipartBody.Part
    ): InsightIdResponse

    suspend fun getInsightsByAptComplex(
        page: Int,
        size: Int,
        direction: String?,
        properties: List<String>?,
        aptComplex: String
    ): PagingResponse<InsightResponse, InsightDto>
}
