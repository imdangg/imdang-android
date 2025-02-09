package info.imdang.domain.repository

import androidx.paging.PagingData
import info.imdang.domain.model.aptcomplex.VisitAptComplexDto
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.insight.InsightDetailDto
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.model.insight.InsightIdDto
import info.imdang.domain.model.insight.request.WriteInsightDto
import java.io.File
import kotlinx.coroutines.flow.Flow

interface InsightRepository {

    suspend fun writeInsight(writeInsightDto: WriteInsightDto, mainImage: File): InsightIdDto

    suspend fun updateInsight(writeInsightDto: WriteInsightDto, mainImage: File?): InsightIdDto

    /**
     * 인사이트 목록 조회 (paging x)
     */
    suspend fun getInsights(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingDto<InsightDto>

    /**
     * 인사이트 목록 조회 (paging o)
     */
    suspend fun getInsightsWithPaging(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>>

    /**
     * 단지별 인사이트 목록 조회 (paging x)
     */
    suspend fun getInsightsByAptComplex(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        aptComplex: String
    ): PagingDto<InsightDto>

    /**
     * 단지별 인사이트 목록 조회 (paging o)
     */
    suspend fun getInsightsByAptComplexWithPaging(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        aptComplex: String,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>>

    suspend fun getInsightsByAddress(
        siGunGu: String,
        eupMyeonDong: String,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>>

    suspend fun getInsightsByDate(
        date: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): PagingDto<InsightDto>

    suspend fun getInsightsByDateWithPaging(
        date: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>>

    suspend fun getInsightDetail(insightId: String): InsightDetailDto

    suspend fun recommendInsight(
        insightId: String,
        memberId: String
    ): InsightIdDto

    suspend fun reportInsight(
        insightId: String,
        memberId: String
    ): InsightIdDto

    suspend fun getVisitedAptComplexes(): List<VisitAptComplexDto>
}
