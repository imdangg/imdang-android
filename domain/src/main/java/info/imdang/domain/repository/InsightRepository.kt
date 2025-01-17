package info.imdang.domain.repository

import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.model.insight.InsightIdDto
import info.imdang.domain.model.insight.request.WriteInsightDto
import java.io.File

interface InsightRepository {

    suspend fun writeInsight(writeInsightDto: WriteInsightDto, mainImage: File): InsightIdDto

    suspend fun getInsightsByAptComplex(
        page: Int,
        size: Int,
        direction: String?,
        properties: List<String>?,
        aptComplex: String
    ): PagingDto<InsightDto>
}
