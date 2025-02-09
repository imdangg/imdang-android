package info.imdang.data.repository

import android.util.Log
import androidx.paging.PagingData
import com.google.gson.Gson
import info.imdang.data.datasource.remote.InsightRemoteDataSource
import info.imdang.data.mapper.mapper
import info.imdang.data.model.request.insight.RecommendInsightRequest
import info.imdang.data.model.request.insight.ReportInsightRequest
import info.imdang.data.pagingsource.getPagingFlow
import info.imdang.domain.model.aptcomplex.VisitAptComplexDto
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.insight.InsightDetailDto
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.model.insight.InsightIdDto
import info.imdang.domain.model.insight.request.WriteInsightDto
import info.imdang.domain.repository.InsightRepository
import java.io.File
import kotlinx.coroutines.flow.Flow
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

    override suspend fun updateInsight(
        writeInsightDto: WriteInsightDto,
        mainImage: File?
    ): InsightIdDto = insightRemoteDataSource.updateInsight(
        updateInsightCommand = Gson().toJson(writeInsightDto).toRequestBody(
            "application/json".toMediaTypeOrNull()
        ),
        mainImage = if (mainImage != null) {
            MultipartBody.Part.createFormData(
                "mainImage",
                mainImage.name,
                mainImage.asRequestBody()
            )
        } else {
            null
        }
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

    override suspend fun getInsightsWithPaging(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>> = getPagingFlow(
        initialPage = page ?: 0,
        pageSize = size ?: 20,
        loadData = { currentPage, pageSize ->
            insightRemoteDataSource.getInsights(
                page = currentPage,
                size = pageSize,
                direction = direction,
                properties = properties
            ).mapper()
        },
        totalCountListener = totalCountListener
    )

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

    override suspend fun getInsightsByAptComplexWithPaging(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        aptComplex: String,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>> = getPagingFlow(
        initialPage = page ?: 0,
        pageSize = size ?: 20,
        loadData = { currentPage, pageSize ->
            insightRemoteDataSource.getInsightsByAptComplex(
                page = currentPage,
                size = pageSize,
                direction = direction,
                properties = properties,
                aptComplex = aptComplex
            ).mapper()
        },
        totalCountListener = totalCountListener
    )

    override suspend fun getInsightsByAddress(
        siGunGu: String,
        eupMyeonDong: String,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>> = getPagingFlow(
        initialPage = page ?: 0,
        pageSize = size ?: 20,
        loadData = { currentPage, pageSize ->
            insightRemoteDataSource.getInsightsByAddress(
                siGunGu = siGunGu,
                eupMyeonDong = eupMyeonDong,
                page = currentPage,
                size = pageSize,
                direction = direction,
                properties = properties
            ).mapper()
        },
        totalCountListener = totalCountListener
    )

    override suspend fun getInsightsByDate(
        date: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): PagingDto<InsightDto> = insightRemoteDataSource.getInsightsByDate(
        date = date,
        page = page,
        size = size,
        direction = direction,
        properties = properties
    ).mapper()

    override suspend fun getInsightsByDateWithPaging(
        date: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>> = getPagingFlow(
        initialPage = page ?: 0,
        pageSize = size ?: 20,
        loadData = { currentPage, pageSize ->
            insightRemoteDataSource.getInsightsByDate(
                date = date,
                page = currentPage,
                size = pageSize,
                direction = direction,
                properties = properties
            ).mapper()
        },
        totalCountListener = totalCountListener
    )

    override suspend fun getInsightDetail(insightId: String): InsightDetailDto {
        val a = insightRemoteDataSource.getInsightDetail(insightId)
        Log.d("##", a.toString())
        try {
            Log.d("##", a.mapper().toString())
        } catch (e: Exception) {
            Log.e("##", Log.getStackTraceString(e))
        }
        return a.mapper()
    }

    override suspend fun recommendInsight(
        insightId: String,
        memberId: String
    ): InsightIdDto = insightRemoteDataSource.recommendInsight(
        RecommendInsightRequest(
            insightId = insightId,
            recommendMemberId = memberId
        )
    ).mapper()

    override suspend fun reportInsight(
        insightId: String,
        memberId: String
    ): InsightIdDto = insightRemoteDataSource.reportInsight(
        ReportInsightRequest(
            insightId = insightId,
            accuseMemberId = memberId
        )
    ).mapper()

    override suspend fun getVisitedAptComplexes(): List<VisitAptComplexDto> =
        insightRemoteDataSource.getVisitedAptComplexes().mapper()
}
