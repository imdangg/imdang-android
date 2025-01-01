package info.imdang.domain.usecase.home

import info.imdang.domain.DefaultDispatcher
import info.imdang.domain.repository.HomeRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SetCloseTimeOfHomeFreePassUseCase @Inject constructor(
    private val repository: HomeRepository,
    @DefaultDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Long, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Long) =
        repository.setCloseTimeOfHomeFreePassPopup(parameters)
}
