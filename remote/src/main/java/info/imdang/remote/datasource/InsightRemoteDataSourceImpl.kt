package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.InsightRemoteDataSource
import info.imdang.data.model.response.insight.InsightIdResponse
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
}
