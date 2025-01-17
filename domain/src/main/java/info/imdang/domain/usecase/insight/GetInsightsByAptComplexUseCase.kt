package info.imdang.domain.usecase.insight

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetInsightsByAptComplexUseCase @Inject constructor(
    private val insightRepository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<GetInsightsByAptComplexParams, PagingDto<InsightDto>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: GetInsightsByAptComplexParams
    ): PagingDto<InsightDto> = insightRepository.getInsightsByAptComplex(
        page = parameters.page,
        size = parameters.size,
        direction = parameters.direction,
        properties = parameters.properties,
        aptComplex = parameters.aptComplex
    )
}

data class GetInsightsByAptComplexParams(
    val page: Int = 1,
    val size: Int = 20,
    val direction: String? = null,
    val properties: List<String>? = null,
    val aptComplex: String
)
