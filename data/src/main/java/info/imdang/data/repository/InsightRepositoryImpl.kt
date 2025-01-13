package info.imdang.data.repository

import info.imdang.data.datasource.remote.InsightRemoteDataSource
import info.imdang.data.model.request.insight.WriteInsightRequest
import info.imdang.domain.model.insight.InsightIdDto
import info.imdang.domain.model.insight.request.WriteInsightDto
import info.imdang.domain.repository.InsightRepository
import javax.inject.Inject

internal class InsightRepositoryImpl @Inject constructor(
    private val insightRemoteDataSource: InsightRemoteDataSource
) : InsightRepository {

    override suspend fun writeInsight(writeInsightDto: WriteInsightDto): InsightIdDto =
        insightRemoteDataSource.writeInsight(
            WriteInsightRequest.fromDto(writeInsightDto)
        ).mapper()
}
