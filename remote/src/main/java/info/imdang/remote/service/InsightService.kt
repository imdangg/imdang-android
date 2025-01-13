package info.imdang.remote.service

import info.imdang.data.model.request.insight.WriteInsightRequest
import info.imdang.data.model.response.insight.InsightIdResponse
import retrofit2.http.Body
import retrofit2.http.POST

internal interface InsightService {

    /** 인사이트 작성 */
    @POST("insights/create")
    suspend fun writeInsight(
        @Body writeInsightRequest: WriteInsightRequest
    ): InsightIdResponse
}
