package info.imdang.domain.usecase.insight

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.insight.InsightIdDto
import info.imdang.domain.model.insight.request.WriteInsightDto
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.usecase.UseCase
import java.io.File
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdateInsightUseCase @Inject constructor(
    private val insightRepository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<UpdateInsightParams, InsightIdDto>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: UpdateInsightParams): InsightIdDto =
        insightRepository.updateInsight(
            parameters.writeInsightDto,
            parameters.mainImage
        )
}

data class UpdateInsightParams(
    val writeInsightDto: WriteInsightDto,
    val mainImage: File?
)
