package info.imdang.data.repository

import com.google.gson.Gson
import info.imdang.data.datasource.remote.InsightRemoteDataSource
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.insight.InsightDetailDto
import info.imdang.domain.model.insight.InsightDto
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

    override suspend fun getInsights(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingDto<InsightDto> = insightRemoteDataSource.getInsights(
        page = page,
        size = size,
        direction = direction,
        properties = properties
    ).mapper()

    override suspend fun getInsightsByAptComplex(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        aptComplex: String
    ): PagingDto<InsightDto> = insightRemoteDataSource.getInsightsByAptComplex(
        page = page,
        size = size,
        direction = direction,
        properties = properties,
        aptComplex = aptComplex
    ).mapper()

    override suspend fun getInsightDetail(insightId: String): InsightDetailDto =
        insightRemoteDataSource.getInsightDetail(insightId).mapper()
}
