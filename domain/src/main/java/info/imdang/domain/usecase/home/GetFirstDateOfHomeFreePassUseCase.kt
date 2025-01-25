package info.imdang.domain.usecase.home

import info.imdang.domain.DefaultDispatcher
import info.imdang.domain.repository.HomeRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetFirstDateOfHomeFreePassUseCase @Inject constructor(
    private val repository: HomeRepository,
    @DefaultDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, Long>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Unit): Long =
        repository.getFirstOpenDateOfHomeFreePassPopup()
}
