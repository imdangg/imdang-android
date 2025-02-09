package info.imdang.domain.usecase.insight

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetInsightsByDateUseCase @Inject constructor(
    private val insightRepository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<GetInsightsByDateParams, PagingDto<InsightDto>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: GetInsightsByDateParams
    ): PagingDto<InsightDto> = insightRepository.getInsightsByDate(
        date = parameters.date,
        page = parameters.pagingParams.page - 1,
        size = parameters.pagingParams.size,
        direction = parameters.pagingParams.direction,
        properties = parameters.pagingParams.properties,
        totalCountListener = parameters.pagingParams.totalCountListener
    )
}

data class GetInsightsByDateParams(
    val date: String? = null,
    val pagingParams: PagingParams
)
