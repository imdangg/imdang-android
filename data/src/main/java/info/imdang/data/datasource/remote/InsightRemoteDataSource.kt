package info.imdang.data.datasource.remote

import info.imdang.data.model.request.insight.WriteInsightRequest
import info.imdang.data.model.response.insight.InsightIdResponse

interface InsightRemoteDataSource {

    suspend fun writeInsight(writeInsightRequest: WriteInsightRequest): InsightIdResponse
}
