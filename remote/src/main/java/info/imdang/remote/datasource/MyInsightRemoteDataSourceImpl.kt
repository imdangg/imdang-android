package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.MyInsightRemoteDataSource
import info.imdang.data.model.response.myinsight.AptComplexResponse
import info.imdang.data.model.response.common.PagingResponse
import info.imdang.data.model.response.insight.InsightResponse
import info.imdang.data.model.response.myinsight.MyInsightAddressResponse
import info.imdang.domain.model.insight.InsightDto
import info.imdang.remote.service.MyInsightService
import javax.inject.Inject

internal class MyInsightRemoteDataSourceImpl @Inject constructor(
    private val myInsightService: MyInsightService
) : MyInsightRemoteDataSource {

    override suspend fun getAddresses(): List<MyInsightAddressResponse> =
        myInsightService.getAddresses()

    override suspend fun getComplexesByAddress(
        queries: Map<String, String>
    ): List<AptComplexResponse> = myInsightService.getComplexesByAddress(queries)

    override suspend fun getMyInsightsByAddress(
        addressQueries: Map<String, String>,
        aptComplexName: String?,
        onlyMine: Boolean?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto> = myInsightService.getMyInsightsByAddress(
        addressQueries = addressQueries,
        aptComplexName = aptComplexName,
        onlyMine = onlyMine,
        page = page,
        size = size,
        direction = direction,
        properties = properties
    )

    override suspend fun getMyInsights(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto> = myInsightService.getMyInsights(
        page = page,
        size = size,
        direction = direction,
        properties = properties
    )
}
