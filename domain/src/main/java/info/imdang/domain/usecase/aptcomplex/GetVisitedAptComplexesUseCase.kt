package info.imdang.domain.usecase.aptcomplex

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.aptcomplex.VisitedAptComplexDto
import info.imdang.domain.repository.AptComplexRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetVisitedAptComplexesUseCase @Inject constructor(
    private val aptComplexRepository: AptComplexRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, List<VisitedAptComplexDto>>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Unit): List<VisitedAptComplexDto> =
        aptComplexRepository.getVisitedAptComplexes()
}
