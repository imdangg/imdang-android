package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.InsightRemoteDataSource
import info.imdang.data.model.request.insight.WriteInsightRequest
import info.imdang.data.model.response.insight.InsightIdResponse
import info.imdang.remote.service.InsightService
import javax.inject.Inject

internal class InsightRemoteDataSourceImpl @Inject constructor(
    private val insightService: InsightService
) : InsightRemoteDataSource {

    override suspend fun writeInsight(writeInsightRequest: WriteInsightRequest): InsightIdResponse =
        insightService.writeInsight(writeInsightRequest)
}
