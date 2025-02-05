package info.imdang.data.datasource.remote

import info.imdang.data.model.response.myinsight.AptComplexResponse
import info.imdang.data.model.response.common.PagingResponse
import info.imdang.data.model.response.insight.InsightResponse
import info.imdang.data.model.response.myinsight.MyInsightAddressResponse
import info.imdang.domain.model.insight.InsightDto

interface MyInsightRemoteDataSource {

    suspend fun getAddresses(): List<MyInsightAddressResponse>

    suspend fun getComplexesByAddress(queries: Map<String, String>): List<AptComplexResponse>

    suspend fun getMyInsightsByAddress(
        addressQueries: Map<String, String>,
        aptComplexName: String?,
        onlyMine: Boolean?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto>

    suspend fun getMyInsights(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto>
}
