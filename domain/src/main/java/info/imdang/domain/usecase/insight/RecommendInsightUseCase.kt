package info.imdang.domain.usecase.insight

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.insight.InsightIdDto
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RecommendInsightUseCase @Inject constructor(
    private val insightRepository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<RecommendInsightParams, InsightIdDto>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(parameters: RecommendInsightParams): InsightIdDto =
        insightRepository.recommendInsight(
            insightId = parameters.insightId,
            memberId = parameters.memberId
        )
}

data class RecommendInsightParams(
    val insightId: String,
    val memberId: String
)
