package info.imdang.domain.repository

import info.imdang.domain.model.insight.InsightIdDto
import info.imdang.domain.model.insight.request.WriteInsightDto
import java.io.File

interface InsightRepository {

    suspend fun writeInsight(writeInsightDto: WriteInsightDto, mainImage: File): InsightIdDto
}
