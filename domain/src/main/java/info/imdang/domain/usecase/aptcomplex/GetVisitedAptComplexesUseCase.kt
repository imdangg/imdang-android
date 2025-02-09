package info.imdang.domain.usecase.aptcomplex

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.aptcomplex.VisitAptComplexDto
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetVisitedAptComplexesUseCase @Inject constructor(
    private val insightRepository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, List<VisitAptComplexDto>>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Unit): List<VisitAptComplexDto> =
        insightRepository.getVisitedAptComplexes()
}
