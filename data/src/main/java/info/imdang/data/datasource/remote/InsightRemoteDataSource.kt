package info.imdang.data.datasource.remote

import info.imdang.data.model.response.insight.InsightIdResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface InsightRemoteDataSource {

    suspend fun writeInsight(
        createInsightCommand: RequestBody,
        mainImage: MultipartBody.Part
    ): InsightIdResponse
}
