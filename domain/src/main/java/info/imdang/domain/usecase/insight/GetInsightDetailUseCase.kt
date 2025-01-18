package info.imdang.domain.usecase.insight

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.insight.InsightDetailDto
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetInsightDetailUseCase @Inject constructor(
    private val insightRepository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, InsightDetailDto>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(parameters: String): InsightDetailDto =
        insightRepository.getInsightDetail(parameters)
}
