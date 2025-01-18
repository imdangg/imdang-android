package info.imdang.data.repository

import com.google.gson.Gson
import info.imdang.data.datasource.remote.InsightRemoteDataSource
import info.imdang.domain.model.insight.InsightIdDto
import info.imdang.domain.model.insight.request.WriteInsightDto
import info.imdang.domain.repository.InsightRepository
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

internal class InsightRepositoryImpl @Inject constructor(
    private val insightRemoteDataSource: InsightRemoteDataSource
) : InsightRepository {

    override suspend fun writeInsight(
        writeInsightDto: WriteInsightDto,
        mainImage: File
    ): InsightIdDto = insightRemoteDataSource.writeInsight(
        createInsightCommand = Gson().toJson(writeInsightDto).toRequestBody(
            "application/json".toMediaTypeOrNull()
        ),
        mainImage = MultipartBody.Part.createFormData(
            "mainImage",
            mainImage.name,
            mainImage.asRequestBody()
        )
    ).mapper()
}
