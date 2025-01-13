package info.imdang.domain.repository

import info.imdang.domain.model.insight.InsightIdDto
import info.imdang.domain.model.insight.request.WriteInsightDto

interface InsightRepository {

    suspend fun writeInsight(writeInsightDto: WriteInsightDto): InsightIdDto
}
