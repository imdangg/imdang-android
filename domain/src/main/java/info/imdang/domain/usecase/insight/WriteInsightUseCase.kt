package info.imdang.domain.usecase.insight

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.insight.InsightIdDto
import info.imdang.domain.model.insight.request.WriteInsightDto
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class WriteInsightUseCase @Inject constructor(
    private val insightRepository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<WriteInsightDto, InsightIdDto>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: WriteInsightDto): InsightIdDto {
        return insightRepository.writeInsight(parameters)
    }
}
