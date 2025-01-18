package info.imdang.remote.service

import info.imdang.data.model.response.insight.InsightIdResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

internal interface InsightService {

    /** 인사이트 작성 */
    @Multipart
    @POST("insights/create")
    suspend fun writeInsight(
        @Part("createInsightCommand") createInsightCommand: RequestBody,
        @Part mainImage: MultipartBody.Part
    ): InsightIdResponse
}
